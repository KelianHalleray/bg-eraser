package com.kelian.mask.model;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Mask other = (Mask) obj;
        return Arrays.deepEquals(pixelsStatus, other.pixelsStatus);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(pixelsStatus);
    }
}
