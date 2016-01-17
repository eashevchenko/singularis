package org.shevalab.singularis.desktop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;

public class ImageResizer {

    public static void resize(File inputFile,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {

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

    public static void main(String[] args) throws URISyntaxException {

        int scaledWidth = 128;
        int scaledHeight = 128;

	    File file = new File("in");
	    if(file.isDirectory()){
		    File[] idles = file.listFiles(new FilenameFilter() {
			    @Override
			    public boolean accept(File dir, String name) {
				    return name.startsWith("Idle");
			    }
		    });
		    for(File inFile : idles) {
			    try {
				    ImageResizer.resize(inFile, "out/" + file.getName(), scaledWidth, scaledHeight);
			    } catch (IOException e) {
				    e.printStackTrace();
			    }
		    }
	    }
    }

    private static String formatNumber(int i){
        String format = i < 10 ? "0%d" : "%d";
        return String.format(format, i);
    }

}