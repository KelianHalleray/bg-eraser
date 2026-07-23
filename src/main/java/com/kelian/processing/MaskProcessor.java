package com.kelian.processing;

import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;


/**
 * Applies post-processing operations to a mask.
 */
public class MaskProcessor {

    /**
     * Minimum number of neighbors required to change a pixel.
     */
    private static final int NEIGHBOR_MAJORITY_THRESHOLD = 4;

    /**
     * Processes the given mask.
     *
     * @param sourceMask the mask to process
     * @return the processed mask
     */
    public Mask process(Mask sourceMask) {
        Mask processedMask = copyMask(sourceMask);
        applyMajorityFilter(sourceMask, processedMask);

        return processedMask;
    }

    /**
     * Applies a majority filter to the mask.
     *
     * @param sourceMask the original mask
     * @param destinationMask the processed mask
     */
    private void applyMajorityFilter(Mask sourceMask, Mask destinationMask) {
        int height = sourceMask.getHeight();
        int width = sourceMask.getWidth();

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int countBackgroundNeighbor = 0;
                int countSubjectNeighbor = 0;

                for (int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
                    for (int neighborX = x - 1; neighborX <= x + 1; neighborX++) {

                        if (neighborX == x && neighborY == y) {
                            continue;
                        }

                        PixelStatus neighborStatus = sourceMask.getStatus(neighborX, neighborY);

                        if (neighborStatus == PixelStatus.BACKGROUND) {
                            countBackgroundNeighbor++;
                        } else if (neighborStatus == PixelStatus.SUBJECT) {
                            countSubjectNeighbor++;
                        }
                    }
                }

                if (countBackgroundNeighbor > NEIGHBOR_MAJORITY_THRESHOLD) {
                    destinationMask.setStatus(x, y, PixelStatus.BACKGROUND);

                } else if (countSubjectNeighbor > NEIGHBOR_MAJORITY_THRESHOLD) {
                    destinationMask.setStatus(x, y, PixelStatus.SUBJECT);
                }
            }
        }
    }

    /**
     * Creates a copy of the given mask.
     *
     * @param mask the mask to copy
     * @return a copy of the mask
     */
    private Mask copyMask(Mask mask) {
        int height = mask.getHeight();
        int width = mask.getWidth();
        PixelStatus[][] processedPixels = new PixelStatus[height][width];
        Mask copiedMask = new Mask(processedPixels);


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copiedMask.setStatus(x, y, mask.getStatus(x, y));
            }
        }

        return copiedMask;
    }
}
