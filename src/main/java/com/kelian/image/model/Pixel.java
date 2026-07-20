package com.kelian.image.model;

public class Pixel {
    private final int x;
    private final int y;
    private final int color;

    public Pixel(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public int withAlpha(int alpha) {
        return (alpha << 24) | (color & 0x00FFFFFF);
    }

    public int getAlpha() {
        return (color >>> 24) & 0xFF;
    }

    public int getRed() {
        return (color >>> 16) & 0xFF;
    }

    public int getGreen() {
        return (color >>> 8) & 0xFF;
    }

    public int getBlue() {
        return color & 0xFF;
    }
}
