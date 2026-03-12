package org.example.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileRead { // TODO: FileWrite + Javadoc
    private File inputFile;
    private File outputFile;

    public FileRead(String inputPath, String outputPath) {
        inputFile = new File(inputPath);
        outputFile = new File(outputPath);
    }

    public ArrayList<String> readInput () {
        ArrayList<String> output = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                for (String wrd : line) {
                    output.add(wrd.strip());
                }
            }
        } catch (IOException e) {
            System.out.println("Error - could not find file at: " + inputFile.getPath());
            output.clear();
        }
        return output;
    }
}
