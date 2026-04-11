import org.example.ui.UIController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class that handles e2e testing. To check the .xml files output, go to /build/resources/test/e2e/outptut/..
 */
public class E2ETest {
    /**
     * Constant long that represents the upper bound of the reasonable timeframe for producing output.
     */
    final long TOLERANCE = 3L;

    @Test
    public void simpleDiagramTestOne() {
        testTimeTolerance(
                "e2e/input/SegwayTest.rct",
                "e2e/output/SegwayTest.xml"
        );
    }

    @Test
    public void simpleDiagramTestTwo() {
        testTimeTolerance(
                "e2e/input/TransportTest.rct",
                "e2e/output/TransportTest.xml"
        );
    }

    @Test
    public void midSizeDiagramTest() {
        testTimeTolerance(
                "e2e/input/AlphaAlgorithmTest.rct",
                "e2e/output/AlphaAlgorithmTest.xml"
        );
    }

    @Test
    public void largeDiagramTest() {
        testTimeTolerance(
                "e2e/input/AutonomousChemicalDetectorTest.rct",
                "e2e/output/AutonomousChemicalDetectorTest.xml"
        );
    }

    @Test
    public void deepDiagramTest() {
        testTimeTolerance(
                "e2e/input/ExplorationTest.rct",
                "e2e/output/ExplorationTest.xml"
        );
    }

    /**
     * Tests whether an input file can be successfully transformed within a reasonable amount of time.
     * @param inputPath
     * string representing the file path of the input .rct file.
     * @param outputPath
     * string representing the file path of the output .xml file.
     */
    private void testTimeTolerance(String inputPath, String outputPath) {
        UIController uiController = new UIController();
        LocalTime before = LocalTime.now();
        uiController.executeTransform(
                E2ETest.class.getClassLoader().getResource(inputPath).getPath(),
                E2ETest.class.getClassLoader().getResource(outputPath).getPath()
        );
        LocalTime after = LocalTime.now();
        assertTrue(ChronoUnit.SECONDS.between(before, after) < TOLERANCE);
    }
}
