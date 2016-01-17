package org.shevalab.singularis.desktop;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;

public class ImageResizer {

    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {

        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public static void main(String[] args) {

        int scaledWidth = 128;
        int scaledHeight = 128;

        for (int i = 0; i <= 11; i++) {
            String inputImagePath = "in/Idle_0"+formatNumber(i)+".png";
            String outputImagePath1 = "out/Idle_0"+formatNumber(i)+".png";
            System.out.println(inputImagePath);
            System.out.println(outputImagePath1);
            try {

                ImageResizer.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
            } catch (IOException ex) {
                System.out.println("Error resizing the image.");
                ex.printStackTrace();
            }
        }
    }

    private static String formatNumber(int i){
        String format = i < 10 ? "0%d" : "%d";
        return String.format(format, i);
    }

}