import com.kelian.image.model.ImageData;
import com.kelian.image.model.Pixel;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;
import com.kelian.processing.TransparencyApplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransparencyApplierTest {
    private TransparencyApplier transparencyApplier;
    private ImageData imageData;
    private Mask mask;

    @BeforeEach
    void setUp() {
        Pixel[][] pixels = {
                {
                        new Pixel(0, 0, 0xFF000000),
                        new Pixel(1, 0, 0xFFFFFFFF)
                },
                {
                        new Pixel(0, 1, 0xFFFF0000),
                        new Pixel(1, 1, 0xFF00FF00)
                }
        };
        imageData = new ImageData("image-test", 2, 2, pixels);
        PixelStatus[][] pixelsStatus = {
                {PixelStatus.BACKGROUND, PixelStatus.SUBJECT},
                {PixelStatus.SUBJECT, PixelStatus.BACKGROUND}
        };

        mask = new Mask(pixelsStatus);
        transparencyApplier = new TransparencyApplier();

    }

    @Test
    void shouldMakeBackgroundPixelsTransparent() {
        // When
        BufferedImage result = transparencyApplier.apply(imageData, mask);

        // Then
        assertEquals(0x00000000, result.getRGB(0, 0));
        assertEquals(0x0000FF00, result.getRGB(1, 1));
    }

    @Test
    void shouldKeepSubjectPixelsColor() {
        // When
        BufferedImage result = transparencyApplier.apply(imageData, mask);

        // Then
        assertEquals(0xFFFFFFFF, result.getRGB(1, 0));
        assertEquals(0xFFFF0000, result.getRGB(0, 1));
    }
}
