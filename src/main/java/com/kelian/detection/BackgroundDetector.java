package com.kelian.detection;

import com.kelian.image.model.ImageData;
import com.kelian.image.model.Pixel;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;

public class BackgroundDetector {

    public Mask detect(ImageData imageData) {
        Pixel[][] pixels = imageData.getPixels();
        int width = imageData.getWidth();
        int height = imageData.getHeight();
        PixelStatus[][] pixelsStatus = new PixelStatus[height][width];
        Mask mask = new Mask(pixelsStatus);

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                Pixel pixel = pixels[y][x];

                if (pixel.getGreen() > pixel.getRed() &&  pixel.getGreen() > pixel.getBlue()) {
                    mask.setStatus(x, y, PixelStatus.BACKGROUND);
                } else {
                    mask.setStatus(x, y, PixelStatus.SUBJECT);
                }
            }
        }

        return mask;
    }
}
