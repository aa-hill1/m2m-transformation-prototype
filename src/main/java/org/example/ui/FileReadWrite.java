package org.example.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReadWrite { // TODO: Javadoc
    private File inputFile;
    private File outputFile;

    public FileReadWrite(String inputPath, String outputPath) throws FileNotFoundException {
        inputFile = new File(inputPath);
        if (inputPath.isEmpty()) {
            throw new RuntimeException("Error - no input file supplied");
        } else if (!inputFile.exists()) {
            throw new FileNotFoundException(String.format("Error - file at: %s does not exist", inputPath));
        }
        outputFile = new File(outputPath);
        if (outputPath.isEmpty()) {
            throw new RuntimeException("Error - no output file supplied");
        } else if (!outputPath.contains(".xml")) {
            throw new RuntimeException("Error - output file in incorrect format");
        }
    }

    public ArrayList<String> readInput() {
        ArrayList<String> inputData = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                for (String wrd : line) {
                    inputData.add(wrd.strip());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file at: " + inputFile.getPath());
        }
        if (inputData.isEmpty()) {
            throw new RuntimeException(String.format("Error - file at %s is empty", inputFile.getPath()));
        }
        return inputData;
    }

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
