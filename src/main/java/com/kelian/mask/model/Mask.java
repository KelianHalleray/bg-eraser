package com.kelian.mask.model;

public class Mask {
    private final PixelStatus[][] pixelsStatus;

    public Mask(PixelStatus[][] pixelsStatus) {
        this.pixelsStatus = pixelsStatus;
    }

    public PixelStatus getStatus(int x, int y) {
        return pixelsStatus[y][x];
    }

    public void setStatus(int x, int y, PixelStatus status) {
        pixelsStatus[y][x] = status;
    }

    public int getWidth() {
        return pixelsStatus[0].length;
    }

    public int getHeight() {
        return pixelsStatus.length;
    }

}
