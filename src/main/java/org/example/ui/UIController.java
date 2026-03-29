package org.example.ui;

import org.example.app.TransformEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UIController { //TODO: Javadoc

    public UIController() {}

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

            try {
                FileReadWrite reader = new FileReadWrite(inputFilePath, outputFilePath);
                ArrayList<String> inputData = reader.readInput();

                TransformEngine engine = new TransformEngine(inputData);
                if (reader.writeOutput(engine.transform())) {
                    System.out.println("File successfully transformed and output");
                }
            } catch (RuntimeException | FileNotFoundException e) {
                System.out.println(e.getMessage() + "\n\n");
            }
        }
    }
}
