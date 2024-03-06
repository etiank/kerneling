import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ParKerneling {

    public void kerneling(String fileName, String directory, float[][] kernel){

        long t0 = System.currentTimeMillis();
        long t;

        //TRBLSHT
        System.out.println("File Path: " + directory + fileName);


        try {
            BufferedImage image = ImageIO.read(new File(directory + fileName));
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.invoke(new KernelingTask(image, resultImage, kernel, 1, height-1, 1 ,width-1));



            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {

                        // passa una volta per colore (RGB), per grayscale basterebbe un valore
                    int sumRed = 0; int sumGreen = 0; int sumBlue = 0;

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Color pixelColor = new Color(image.getRGB(x+j, y+i));
                            sumRed += pixelColor.getRed() * kernel[i+1][j+1];
                            sumGreen += pixelColor.getGreen() * kernel[i+1][j+1];
                            sumBlue += pixelColor.getBlue() * kernel[i+1][j+1];
                        }
                    }

                    int red = Math.min(255, Math.max(0, sumRed));
                    int green = Math.min(255, Math.max(0, sumGreen));
                    int blue = Math.min(255, Math.max(0, sumBlue));

                    red = (int) (255.0 * red / 255.0);
                    green = (int) (255.0 * green / 255.0);
                    blue = (int) (255.0 * blue / 255.0);

                    int rgb = new Color(red, green, blue).getRGB();
                    resultImage.setRGB(x, y, rgb);
                }
            }

            ImageIO.write(resultImage, "png", new File("output.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        t = System.currentTimeMillis() - t0;
        System.out.println("The convolution process took " + t + "ms");
        openImage();

    }

    public void openImage(){
        JFrame frame = new JFrame("Output image");
        ImageIcon icon = new ImageIcon("cat2.jpeg");
        frame.setIconImage(icon.getImage());
        frame.setSize(500, 500);

        BufferedImage displayOutput;
        try {
            displayOutput = ImageIO.read(new File("output.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel outputPreview = new JLabel(new ImageIcon(displayOutput));

        JScrollPane scroll = new JScrollPane(new JLabel(new ImageIcon(displayOutput)));
        scroll.add(outputPreview);

        frame.add(scroll);
        frame.setLocation(450,300);
        frame.setVisible(true);
    }


    private class KernelingTask extends RecursiveAction{

        private final BufferedImage image;
        private final BufferedImage resultImage;
        private final float[][] kernel;
        private final int startY, endY;
        private final int startX, endX;

        public KernelingTask(BufferedImage image, BufferedImage resultImage, float[][] kernel, int startY, int endY, int startX, int endX) {
            this.image = image;
            this.resultImage = resultImage;
            this.kernel = kernel;
            this.startY = startY;
            this.endY = endY;
            this.startX = startX;
            this.endX = endX;
        }

        @Override
        protected void compute() {
            if (endY - startY <= 4 || endX - startX <=4){   //threshold
                for (int y = startY; y < endY; y++) {
                    for (int x = startX; x < endX; x++) {

                        int sumRed = 0;
                        int sumGreen = 0;
                        int sumBlue = 0;

                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {

                                int neighborX = x + j; int neighborY = y + i;

                                if (neighborX < 0) {
                                    neighborX = 0; // replica left edge pixel
                                } else if (neighborX >= image.getWidth()) {
                                    neighborX = image.getWidth() - 1; // replica right edge pixel
                                }
                                if (neighborY < 0) {
                                    neighborY = 0; // replica top edge pixel
                                } else if (neighborY >= image.getHeight()) {
                                    neighborY = image.getHeight() - 1; // replica edge pixel sotto
                                }

                                Color pixelColor = new Color(image.getRGB(neighborX, neighborY));
                                sumRed += pixelColor.getRed() * kernel[i + 1][j + 1];
                                sumGreen += pixelColor.getGreen() * kernel[i + 1][j + 1];
                                sumBlue += pixelColor.getBlue() * kernel[i + 1][j + 1];
                            }
                        }

                        int red = Math.min(255, Math.max(0, sumRed));
                        int green = Math.min(255, Math.max(0, sumGreen));
                        int blue = Math.min(255, Math.max(0, sumBlue));

                        red = (int) (255.0 * red / 255.0);
                        green = (int) (255.0 * green / 255.0);
                        blue = (int) (255.0 * blue / 255.0);

                        int rgb = new Color(red, green, blue).getRGB();
                        resultImage.setRGB(x, y, rgb);

                    }
                }
            } else {
                int midY = startY + (endY - startY) / 2;
                int midX = startX + (endX - startX) / 2;
                invokeAll(
                        new KernelingTask(image, resultImage, kernel, startY, midY, startX, midX),
                        new KernelingTask(image, resultImage, kernel, startY, midY, midX, endX),
                        new KernelingTask(image, resultImage, kernel, midY, endY, startX, midX),
                        new KernelingTask(image, resultImage, kernel, midY, endY, midX, endX)
                );
            }
        }
    }
}
