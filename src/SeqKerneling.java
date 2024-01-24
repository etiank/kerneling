import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class SeqKerneling {

    // the allmighty convolution algorithm
    public void test(String fileName, String directory, float[][] kernel){

        try {
            BufferedImage image = ImageIO.read(new File(fileName));

            BufferedImage resultImage = applyConvolution(image, kernel);
            ImageIO.write(resultImage, "png", new File("output.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static BufferedImage applyConvolution(BufferedImage image, float[][] kernel){

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                // Apply convolution
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        Color pixelColor = new Color(image.getRGB(x + j, y + i));
                        sumRed += pixelColor.getRed() * kernel[i + 1][j + 1];
                        sumGreen += pixelColor.getGreen() * kernel[i + 1][j + 1];
                        sumBlue += pixelColor.getBlue() * kernel[i + 1][j + 1];
                    }
                }

                // Update pixel value
                int newRed = Math.min(255, Math.max(0, sumRed / 9));
                int newGreen = Math.min(255, Math.max(0, sumGreen / 9));
                int newBlue = Math.min(255, Math.max(0, sumBlue / 9));
                int newRGB = new Color(newRed, newGreen, newBlue).getRGB();
                resultImage.setRGB(x, y, newRGB);
            }
        }

        return resultImage;
    }


}
