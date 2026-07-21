import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaskTest {
    private Mask mask;

    @BeforeEach
    void setUp() {
        PixelStatus[][] pixelsStatus = {
                {PixelStatus.BACKGROUND, PixelStatus.SUBJECT},
                {PixelStatus.UNDEFINED, PixelStatus.BACKGROUND}
        };

        mask = new Mask(pixelsStatus);
    }

    @Test
    void shouldReturnPixelStatus() {
        // When
        PixelStatus result = mask.getStatus(1, 0);

        // Then
        assertEquals(PixelStatus.SUBJECT, result);
    }

    @Test
    void shouldUpdatePixelStatus() {
        // When
        mask.setStatus(0, 1, PixelStatus.SUBJECT);

        // Then
        assertEquals(PixelStatus.SUBJECT, mask.getStatus(0, 1));
    }

    @Test
    void shouldReturnMaskWidth() {
        // When
        int result = mask.getWidth();

        // Then
        assertEquals(2, result);
    }

    @Test
    void shouldReturnMaskHeight() {
        // When
        int result = mask.getHeight();

        // Then
        assertEquals(2, result);
    }

    @Test
    void shouldReturnCorrectDimensionsForNonSquareMask() {

        // Given
        PixelStatus[][] pixelsStatus = {
                {PixelStatus.UNDEFINED, PixelStatus.UNDEFINED, PixelStatus.UNDEFINED},
                {PixelStatus.UNDEFINED, PixelStatus.UNDEFINED, PixelStatus.UNDEFINED}
        };

        // When
        Mask mask = new Mask(pixelsStatus);
        int resultHeight = mask.getHeight();
        int resultWidth = mask.getWidth();

        // Then
        assertEquals(2, resultHeight);
        assertEquals(3, resultWidth);
    }
}
