import com.kelian.mask.model.Mask;
import com.kelian.mask.model.PixelStatus;
import com.kelian.processing.MaskProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.kelian.mask.model.PixelStatus.BACKGROUND;
import static com.kelian.mask.model.PixelStatus.SUBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class MaskProcessorTest {

    private Mask mask;
    private MaskProcessor maskProcessor;

    @BeforeEach
    void setUp() {
        mask = createDefaultMask();
        maskProcessor = new MaskProcessor();
    }

    @Test
    void shouldKeepMaskUnchangedWhenNoPixelNeedsProcessing() {
        // Given
        PixelStatus[][] pixelsStatus = {
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND}
        };

        Mask originalMask = new Mask(pixelsStatus);

        // When
        Mask processedMask = maskProcessor.process(originalMask);

        // Then
        assertEquals(originalMask, processedMask);
    }

    @Test
    void shouldConvertIsolatedSubjectPixelToBackground() {
        // When
        Mask processedMask = maskProcessor.process(mask);

        // Then
        assertEquals(BACKGROUND, processedMask.getStatus(1, 1));
    }

    @Test
    void shouldConvertIsolatedBackgroundPixelToSubject() {
        // When
        Mask processedMask = maskProcessor.process(mask);

        // Then
        assertEquals(SUBJECT, processedMask.getStatus(2, 2));
    }

    @Test
    void shouldKeepPixelWhenBackgroundAndSubjectNeighborsAreBalanced() {
        // Given
        PixelStatus[][] pixelsStatus = {
                {BACKGROUND, BACKGROUND, SUBJECT},
                {BACKGROUND, SUBJECT,    SUBJECT},
                {SUBJECT,    SUBJECT,    BACKGROUND}
        };

        Mask sourceMask = new Mask(pixelsStatus);

        // When
        Mask processedMask = maskProcessor.process(sourceMask);

        // Then
        assertEquals(SUBJECT, processedMask.getStatus(1, 1));
    }

    @Test
    void shouldNotModifyBorderPixels() {
        // When
        Mask processedMask = maskProcessor.process(mask);

        // Then
        int lastX = mask.getWidth() - 1;
        int lastY = mask.getHeight() - 1;

        for (int x = 0; x < mask.getWidth(); x++) {
            assertEquals(
                    mask.getStatus(x, 0),
                    processedMask.getStatus(x, 0)
            );

            assertEquals(
                    mask.getStatus(x, lastY),
                    processedMask.getStatus(x, lastY)
            );
        }

        for (int y = 0; y < mask.getHeight(); y++) {
            assertEquals(
                    mask.getStatus(0, y),
                    processedMask.getStatus(0, y)
            );

            assertEquals(
                    mask.getStatus(lastX, y),
                    processedMask.getStatus(lastX, y)
            );
        }
    }

    @Test
    void shouldReturnNewMaskWithoutModifyingSourceMask() {
        // Given
        Mask expectedSourceMask = createDefaultMask();

        // When
        Mask processedMask = maskProcessor.process(mask);

        // Then
        assertNotSame(mask, processedMask);
        assertEquals(expectedSourceMask, mask);
        assertEquals(SUBJECT, processedMask.getStatus(2, 2));
    }

    @Test
    void shouldKeepMajorityBackgroundPixelAsBackground() {
        // When
        Mask processedMask = maskProcessor.process(mask);

        // Then
        assertEquals(BACKGROUND, processedMask.getStatus(0, 0));
    }

    @Test
    void shouldKeepMajoritySubjectPixelAsSubject() {
        // When
        Mask processedMask = maskProcessor.process(mask);

        // Then
        assertEquals(SUBJECT, processedMask.getStatus(1, 2));
    }

    private Mask createDefaultMask() {
        PixelStatus[][] pixelsStatus = {
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, SUBJECT,    SUBJECT,    SUBJECT,    BACKGROUND},
                {BACKGROUND, SUBJECT,    BACKGROUND, SUBJECT,    BACKGROUND},
                {BACKGROUND, SUBJECT,    SUBJECT,    SUBJECT,    BACKGROUND},
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND}
        };

        return new Mask(pixelsStatus);
    }
}