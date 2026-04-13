package org.example.ui;

import org.example.app.TransformEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles the textual input loop for the prototype.
 */
public class UIController { // TODO: remove comments for error reporting

    public UIController() {}

    /**
     * Method that controls the input loop, taking user input of the source and target files for transformation.
     */
    public void inputLoop() {
        boolean close = false;
        while (!close) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input path of .rct file to transform or exit to close.");
            String inputFilePath = scanner.nextLine();
            if (inputFilePath.equalsIgnoreCase("exit")) {
                close = true;
                continue;
            }

            System.out.println("Input desired path for .xml file (file path ending in \\output.xml " +
                    "where 'output' is the desired name of the output file), or exit to close.");
            String outputFilePath = scanner.nextLine();
            if (outputFilePath.equalsIgnoreCase("exit")) {
                close = true;
                continue;
            }

            executeTransform(inputFilePath, outputFilePath);
        }
    }

    /**
     * Reads input from the file specified by {@code inputFilePath}, makes a call to TransformEngine to perform the
     * transformation, then writes the received output to the file specified by {@code outputFilePath}.
     * @param inputFilePath
     * string representing the file path of the input .rct file.
     * @param outputFilePath
     * string representing the file path of the output .xml file.
     */
    public void executeTransform(String inputFilePath, String outputFilePath) {
        try {
            FileReadWrite reader = new FileReadWrite(inputFilePath, outputFilePath);
            ArrayList<String> inputData = reader.readInput();

            TransformEngine engine = new TransformEngine(inputData);
            if (reader.writeOutput(engine.transform())) {
                System.out.println("File successfully transformed and output");
            }
        } catch (/*RuntimeException |*/FileNotFoundException e) {
            System.out.println(e.getMessage() + "\n\n");
        }
    }
}
