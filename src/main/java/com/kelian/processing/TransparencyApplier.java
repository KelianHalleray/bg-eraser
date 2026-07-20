package com.kelian.processing;

import com.kelian.image.model.ImageData;
import com.kelian.image.model.Pixel;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;

import java.awt.image.BufferedImage;

public class TransparencyApplier {

    public BufferedImage apply(ImageData imageData, Mask mask) {
        int imageWidth = imageData.getWidth();
        int imageHeight = imageData.getHeight();
        BufferedImage imageProcessed = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Pixel[][] pixels = imageData.getPixels();

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Pixel pixel = pixels[y][x];

                if (mask.getStatus(x, y) == PixelStatus.BACKGROUND) {
                    imageProcessed.setRGB(x, y, pixel.withAlpha(0));
                } else {
                    imageProcessed.setRGB(x, y, pixel.getColor());
                }
            }
        }

        return imageProcessed;
    }
}
