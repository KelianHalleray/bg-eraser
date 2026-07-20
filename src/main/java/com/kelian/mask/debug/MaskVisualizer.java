package com.kelian.mask.debug;

import com.kelian.exception.ImageWriteException;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MaskVisualizer {

    public void visualize(Mask mask, Path outputFile) {
        BufferedImage image = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < mask.getHeight(); y++) {
            for (int x = 0; x < mask.getWidth(); x++) {
                if (mask.getStatus(x, y) == PixelStatus.BACKGROUND) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        try {
            Path absoluteOutput = outputFile.toAbsolutePath();

            Path parent = absoluteOutput.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }


            boolean written = ImageIO.write(image, "png", outputFile.toFile());

            if (!written) {
                throw new ImageWriteException("Aucun writer PNG disponible");
            }

            System.out.println("Masque enregistré dans : " + absoluteOutput);

        } catch (IOException e) {
            throw new ImageWriteException("Impossible d'écrire l'image dans : " + outputFile.toAbsolutePath() + e);
        }

    }
}
