package com.kelian.detection;

import com.kelian.exception.ImageEmptyException;
import com.kelian.image.model.ImageData;
import com.kelian.image.model.Pixel;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;

public class BackgroundDetector {

    private static final double BACKGROUND_TOLERANCE = 50.0;

    public Mask detect(ImageData imageData) {
        Pixel[][] pixels = imageData.getPixels();
        int width = imageData.getWidth();
        int height = imageData.getHeight();
        PixelStatus[][] pixelsStatus = new PixelStatus[height][width];
        Mask mask = new Mask(pixelsStatus);
        Pixel backgroundColor = estimateBackgroundColor(imageData);

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                Pixel pixel = pixels[y][x];

                if (isBackground(pixel, backgroundColor)) {
                    mask.setStatus(x, y, PixelStatus.BACKGROUND);
                } else {
                    mask.setStatus(x, y, PixelStatus.SUBJECT);
                }
            }
        }

        return mask;
    }


    /**
     * Estimates the average color of the pixels located on the image border.
     *
     * @param imageData the image data to analyze
     * @return a pixel containing the estimated background color
     */
    private Pixel estimateBackgroundColor(ImageData imageData) {
        long redPixelsSum = 0;
        long greenPixelsSum = 0;
        long bluePixelsSum = 0;
        long totalPixels = 0;

        Pixel[][] pixels = imageData.getPixels();

        if (pixels == null || pixels.length == 0) {
            throw new ImageEmptyException("Image vide ou invalide");
        }


        for (int x = 0; x < pixels[0].length; x++) {
            redPixelsSum += pixels[0][x].getRed();
            greenPixelsSum += pixels[0][x].getGreen();
            bluePixelsSum += pixels[0][x].getBlue();
            totalPixels++;
        }

        for (int x = 0; x < pixels[pixels.length - 1].length; x++) {
            redPixelsSum += pixels[pixels.length - 1][x].getRed();
            greenPixelsSum += pixels[pixels.length - 1][x].getGreen();
            bluePixelsSum += pixels[pixels.length - 1][x].getBlue();
            totalPixels++;
        }

        for (int y = 1; y < pixels.length - 1; y++) {
            redPixelsSum += pixels[y][0].getRed();
            greenPixelsSum += pixels[y][0].getGreen();
            bluePixelsSum += pixels[y][0].getBlue();
            redPixelsSum += pixels[y][pixels[y].length - 1].getRed();
            greenPixelsSum += pixels[y][pixels[y].length - 1].getGreen();
            bluePixelsSum += pixels[y][pixels[y].length - 1].getBlue();
            totalPixels += 2;
        }


        int averageRed = (int) (redPixelsSum / totalPixels);
        int averageGreen = (int) (greenPixelsSum / totalPixels);
        int averageBlue = (int) (bluePixelsSum / totalPixels);

        int averageColor = (255 << 24)
                | (averageRed << 16)
                | (averageGreen << 8)
                | averageBlue;

        return new Pixel(0, 0, averageColor);
    }


    /**
     * Checks whether a pixel is considered part of the background.
     *
     * @param pixel the pixel to test
     * @param backgroundColor the estimated background color
     * @return {@code true} if the pixel color is within the background tolerance;
     *         {@code false} otherwise
     */
    private boolean isBackground(Pixel pixel, Pixel backgroundColor) {
        int redDifference = pixel.getRed() - backgroundColor.getRed();
        int greenDifference = pixel.getGreen() - backgroundColor.getGreen();
        int blueDifference = pixel.getBlue() - backgroundColor.getBlue();

        double distance = Math.sqrt(
                redDifference * redDifference
                + greenDifference * greenDifference
                + blueDifference * blueDifference
        );

        return distance <= BACKGROUND_TOLERANCE;
    }

}
