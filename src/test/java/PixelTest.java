import com.kelian.image.model.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PixelTest {
    private Pixel pixel;

    @BeforeEach
    void setUp() {
        pixel = new Pixel(0, 0, 0xFF3366CC);
    }

    @Test
    void shouldReturnAlphaComponent() {

        // When
        int result = pixel.getAlpha();

        // Then
        assertEquals(0xFF, result);
    }

    @Test
    void shouldReturnRedComponent() {
        // When
        int result = pixel.getRed();

        // Then
        assertEquals(0x33, result);
    }

    @Test
    void shouldReturnGreenComponent() {
        // When
        int result = pixel.getGreen();

        // Then
        assertEquals(0x66, result);
    }

    @Test
    void shouldReturnBlueComponent() {
        // When
        int result = pixel.getBlue();

        // Then
        assertEquals(0xCC, result);
    }

    @Test
    void shouldReturnPixelWithNewAlpha() {
        // When
        int result = pixel.withAlpha(0);

        // Then
        assertEquals(0x003366CC, result);
    }

    @Test
    void shouldReturnPixelWithOpaqueAlpha() {
        // Given
        Pixel transparentPixel = new Pixel(0, 0, 0x003366CC);

        // When
        int result = transparentPixel.withAlpha(255);

        // Then
        assertEquals(0xFF3366CC, result);
    }
}
