package com.kelian.image.factory;

import com.kelian.image.model.Pixel;

import java.awt.image.BufferedImage;

public class PixelFactory {

    public Pixel[][] create(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        Pixel[][] pixels = new Pixel[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = new Pixel(col, row, img.getRGB(col, row));
                pixels[row][col] = p;
            }
        }

        return pixels;
    }
}
