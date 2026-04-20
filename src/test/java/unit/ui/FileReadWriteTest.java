package unit.ui;

import org.example.ui.FileReadWrite;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensure that FileReadWrite class functions as intended and throws the correct errors
 * when given bad input.
 */
public class FileReadWriteTest {

    @Test
    public void noInputFileSupplied() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            FileReadWrite fileReadWrite = new FileReadWrite("", "outputPath");
        });
        String expectedMsg = "Error - no input file supplied";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void inputFileNotExist() {
        String inputPath =
                FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite").getPath();
        inputPath += "/fakeFile.rct";
        String finalInputPath = inputPath;
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            FileReadWrite fileReadWrite = new FileReadWrite(finalInputPath, "outputPath");
        });
        String expectedMsg = String.format("Error - file at: %s does not exist", inputPath);
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void inputFileWrongFormat() {
        String inputPath =
                FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testWrongFormat.txt").getPath();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            FileReadWrite fileReadWrite = new FileReadWrite(inputPath, "outputPath");
        });
        String expectedMsg = "Error - input file in incorrect format, must be .rct file";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void emptyInputFile() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            String inputPath =
                    FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testEmptyInput.rct").getPath();
            String outputPath =
                    FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testValidOutput.xml").getPath();
            FileReadWrite fileReadWrite = new FileReadWrite(inputPath, outputPath);
            fileReadWrite.readInput();
        });
        String expectedMsg = "Error - file at testEmptyInput.rct is empty";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void noOutputFileSupplied() {
        String inputPath =
                FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testValidInput.rct").getPath();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            FileReadWrite fileReadWrite = new FileReadWrite(inputPath, "");
        });
        String expectedMsg = "Error - no output file supplied";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void outputFileWrongFormat() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            String inputPath =
                    FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testValidInput.rct").getPath();
            String outputPath =
                    FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testWrongFormat.txt").getPath();
            FileReadWrite fileReadWrite = new FileReadWrite(inputPath, outputPath);
        });
        String expectedMsg = "Error - output file in incorrect format, must be .xml file";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void validFiles() throws FileNotFoundException {
        String inputPath =
                FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testValidInput.rct").getPath();
        String outputPath =
                FileReadWriteTest.class.getClassLoader().getResource("unit/fileReadWrite/testValidOutput.xml").getPath();
        FileReadWrite fileReadWrite = new FileReadWrite(inputPath, outputPath);
        String[] inputArray = new String[15];
        inputArray = (fileReadWrite.readInput()).toArray(inputArray);
        String expectedString = "[record, Direction, {, left, :, real, right, :, real, up, :, real, down, :, real, }]";
        assertEquals(expectedString, Arrays.toString(inputArray));

        expectedString = "testOutput";
        fileReadWrite.writeOutput("testOutput");
        try {
            Scanner scanner = new Scanner(new File(outputPath));
            assertEquals(expectedString, scanner.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
