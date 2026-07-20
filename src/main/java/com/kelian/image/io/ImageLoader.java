package com.kelian.image.io;

import com.kelian.exception.ImageLoadException;
import com.kelian.image.factory.PixelFactory;
import com.kelian.image.model.ImageData;
import com.kelian.image.model.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    private final PixelFactory pixelFactory;

    public ImageLoader(PixelFactory pixelFactory) {
        this.pixelFactory = pixelFactory;
    }

    public ImageData load(String resourcePath) {
        String imageName = getImageName(resourcePath);

        try {
            BufferedImage img = ImageIO.read(new File(resourcePath));

            if (img == null) {
                throw new ImageLoadException("Le fichier n'est pas une image valide.");
            }

            int imageHeight = img.getHeight();
            int imageWidth = img.getWidth();
            Pixel[][] pixels = pixelFactory.create(img);

            return new ImageData(imageName, imageHeight, imageWidth, pixels);

        } catch (IOException e) {
            throw new ImageLoadException("L'image n'a pas pu être chargée.", e);
        }
    }

    private static String getImageName(String resourcePath) {
        String[] decomposedUrl = resourcePath.split("/|\\.");

        return decomposedUrl[decomposedUrl.length - 2];
    }

}
