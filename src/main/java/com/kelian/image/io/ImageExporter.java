package com.kelian.image.io;

import com.kelian.exception.ImageWriteException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageExporter {

    public void write(BufferedImage image, Path outputFile) {

        try {
            Path absoluteOutput = outputFile.toAbsolutePath();
            Path parent = absoluteOutput.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            boolean written = ImageIO.write(image, "png", absoluteOutput.toFile());

            if (!written) {
                throw new IllegalArgumentException("Aucun writer PNG disponible");
            }

            System.out.println("Image enregistré : " + absoluteOutput);

        } catch (IOException e) {
            throw new ImageWriteException("Impossible d'écrire l'image dans : " + outputFile.toAbsolutePath() + e);
        }

    }
}
