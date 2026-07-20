package com.kelian;

import com.kelian.detection.BackgroundDetector;
import com.kelian.image.factory.PixelFactory;
import com.kelian.image.io.ImageExporter;
import com.kelian.image.io.ImageLoader;
import com.kelian.image.model.ImageData;
import com.kelian.mask.debug.MaskVisualizer;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;
import com.kelian.processing.TransparencyApplier;

import java.awt.image.BufferedImage;
import java.nio.file.Path;


public class Main {
    public static void main(String[] args) {
        PixelFactory pixelFactory = new PixelFactory();
        ImageLoader imageLoader = new ImageLoader(pixelFactory);
        BackgroundDetector detector = new BackgroundDetector();
        MaskVisualizer visualizer = new MaskVisualizer();
        TransparencyApplier transparencyApplier = new TransparencyApplier();
        ImageExporter exporter = new ImageExporter();
        int backgroundCount = 0;
        int subjectCount = 0;

        ImageData imageData = imageLoader.load("src/main/resources/data/image2.jpg");

        int totalImageSize  = imageData.getHeight() * imageData.getWidth();

        Mask mask = detector.detect(imageData);

        for (int row = 0; row < mask.getHeight(); row++) {
            for (int col = 0; col < mask.getWidth(); col++) {
                if (mask.getStatus(col, row) == PixelStatus.BACKGROUND) {
                    backgroundCount++;
                } else {
                    subjectCount++;
                }
            }
        }

        System.out.println("Taille de l'image total : " + totalImageSize);
        System.out.println("Nombre de pixel fond vert : " + backgroundCount);
        System.out.println("Nombre de pixel sujet : " + subjectCount);
        System.out.println("Taille de l'image contre taille total des pixels vérifiés : " + totalImageSize + " - " + (backgroundCount + subjectCount));

        visualizer.visualize(mask, Path.of("data/debug/image2-mask.png"));

        BufferedImage processedImage = transparencyApplier.apply(imageData, mask);

        exporter.write(processedImage, Path.of("data/image2-transparent.png"));

    }
}
