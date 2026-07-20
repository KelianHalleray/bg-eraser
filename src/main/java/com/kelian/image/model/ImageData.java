package com.kelian.image.model;

public class ImageData {
    private String name;
    private Integer height;
    private Integer width;
    private Pixel[][] pixels;

    public ImageData(String name, Integer height, Integer width, Pixel[][] pixels) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.pixels = pixels;
    }

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }
}
