import com.kelian.detection.BackgroundDetector;
import com.kelian.image.model.ImageData;
import com.kelian.image.model.Pixel;
import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BackgroundDetectorTest {
    private BackgroundDetector backgroundDetector;
    private ImageData imageData;

    @BeforeEach
    void setUp() {
        backgroundDetector = new BackgroundDetector();

        Pixel[][] pixels = {
                {
                        new Pixel(0, 0, 0xFF00FF00),
                        new Pixel(1, 0, 0xFF00FF00),
                        new Pixel(2, 0, 0xFF00FF00)
                },
                {
                        new Pixel(0, 1, 0xFF00FF00),
                        new Pixel(1, 1, 0xFFFF0000),
                        new Pixel(2, 1, 0xFF00FF00)
                },
                {
                        new Pixel(0, 2, 0xFF00FF00),
                        new Pixel(1, 2, 0xFF00FF00),
                        new Pixel(2, 2, 0xFF00FF00)
                }
        };

        imageData = new ImageData("image-test", 3, 3, pixels);
    }

    @Test
    void shouldDetectPixelCloseToBackgroundColor() {
        // When
        Mask mask = backgroundDetector.detect(imageData);

        // Then
        assertEquals(PixelStatus.BACKGROUND, mask.getStatus(0, 0));
    }

    @Test
    void shouldKeepPixelFarFromBackgroundColorAsSubject() {
        // When
        Mask mask = backgroundDetector.detect(imageData);

        // Then
        assertEquals(PixelStatus.SUBJECT, mask.getStatus(1, 1));
    }

}
