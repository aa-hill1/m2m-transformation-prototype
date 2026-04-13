package org.example.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles reading input from .rct files and saving output to .xml files.
 */
public class FileReadWrite {
    /**
     * File representing the .rct file input source for the transformation.
     */
    private File inputFile;
    /**
     * File representing the .xml output destination for the transformation.
     */
    private File outputFile;

    /**
     * Constructs the class
     * @param inputPath
     * string representing the file path of the .rct file to read input from
     * @param outputPath
     * string representing the file path of the .xml file to write output to.
     * @throws RuntimeException
     * if either of the strings supplied are empty or in the wrong format.
     * @throws FileNotFoundException
     * if there is no file at {@code inputPath}.
     */
    public FileReadWrite(String inputPath, String outputPath) throws FileNotFoundException {
        inputFile = new File(inputPath);
        if (inputPath.isEmpty()) {
            throw new RuntimeException("Error - no input file supplied");
        } else if (!inputFile.exists()) {
            throw new FileNotFoundException(String.format("Error - file at: %s does not exist", inputPath));
        } else if (!inputPath.contains(".rct")) {
            throw new RuntimeException("Error - input file in incorrect format, must be .rct file");
        }
        outputFile = new File(outputPath);
        if (outputPath.isEmpty()) {
            throw new RuntimeException("Error - no output file supplied");
        } else if (!outputPath.contains(".xml")) {
            throw new RuntimeException("Error - output file in incorrect format, must be .xml file");
        }
    }

    /**
     * Reads and returns input from {@code inputFile}, formatting it to remove whitespace.
     * @return an ArrayList of strings which represent the contents of the {@code inputFile}, with whitespace removed.
     * @throws RuntimeException
     * if {@code inputFile} is empty.
     */
    public ArrayList<String> readInput() {
        ArrayList<String> inputData = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                for (String wrd : line) {
                    String strippedWrd = wrd.strip();
                    if (!strippedWrd.isEmpty()) {
                        inputData.add(strippedWrd);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file at: " + inputFile.getPath());
        }
        if (inputData.isEmpty()) {
            throw new RuntimeException(String.format("Error - file at %s is empty", inputFile.getPath()));
        }
        return inputData;
    }

    /**
     * Writes the contents of {@code outputToWrite} to {@code outputFile}.
     * @param outputToWrite
     * string to be written to {@code outputFile}.
     * @return true if output is written to {@code outputFile} successfully, otherwise false.
     */
    public boolean writeOutput(String outputToWrite) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(outputToWrite);
        } catch (IOException e) {
            System.out.println("Error writing to output file");
            return false;
        }
        return true;
    }
}
