import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class SeqKerneling {


    // the almighty convolution algorithm
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

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    int sumRed = 0;
                    int sumGreen = 0;
                    int sumBlue = 0;

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

        //OPEN A NEW WINDOW WITH THE GENERATED IMAGE
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
}
