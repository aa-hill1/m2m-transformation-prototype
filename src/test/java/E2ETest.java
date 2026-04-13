import org.example.ui.UIController;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class that handles e2e testing, using a sample of RoboChart diagrams of varying complexity and size.
 * To check the .xml files output, go to /build/resources/test/e2e/outptut/..
 * Diagrams are sourced from the RoboStar website case studies at: https://robostar.cs.york.ac.uk/case_studies/
 */
public class E2ETest {
    /**
     * Constant long that represents the upper bound of the reasonable timeframe for producing output.
     */
    final long TOLERANCE = 3L;

    /**
     * Method tests the transformation of the small .rct file 'SegwayTest'.
     */
    @Test
    public void simpleDiagramTestOne() {
        testTimeTolerance(
                "e2e/input/SegwayTest.rct",
                "e2e/output/SegwayTest.xml"
        );
    }

    /**
     * Method tests the transformation of the small .rct file 'TransportTest'.
     */
    @Test
    public void simpleDiagramTestTwo() {
        testTimeTolerance(
                "e2e/input/TransportTest.rct",
                "e2e/output/TransportTest.xml"
        );
    }

    /**
     * Method tests the transformation of the medium-sized .rct file 'AlphaAlgorithmTest'.
     */
    @Test
    public void midSizeDiagramTest() {
        testTimeTolerance(
                "e2e/input/AlphaAlgorithmTest.rct",
                "e2e/output/AlphaAlgorithmTest.xml"
        );
    }

    /**
     * Method tests the transformation of the large .rct file 'AutonomousChemicalDetectorTest'.
     */
    @Test
    public void largeDiagramTest() {
        testTimeTolerance(
                "e2e/input/AutonomousChemicalDetectorTest.rct",
                "e2e/output/AutonomousChemicalDetectorTest.xml"
        );
    }

    /**
     * Method tests the transformation of the deep .rct file 'ExplorationTest'.
     */
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
