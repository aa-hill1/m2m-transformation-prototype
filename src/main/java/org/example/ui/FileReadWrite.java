package org.example.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReadWrite { // TODO: FileWrite + Javadoc
    private File inputFile;
    private File outputFile;

    public FileReadWrite(String inputPath, String outputPath) {
        inputFile = new File(inputPath);
        outputFile = new File(outputPath);
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
        } catch (FileNotFoundException e) {
            System.out.println("Error - could not find file at: " + inputFile.getPath());
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
