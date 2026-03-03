package org.example.ui;

import org.example.app.TransformEngine;

import java.util.ArrayList;
import java.util.Scanner;

public class UIController {

    public UIController() {
        inputLoop();
    }
    //FileRead reader = new FileRead("\\C:\\\\Users\\\\AAHil\\\\OneDrive\\\\Documents\\\\A\\\\York\\\\PRBX\\\\F3 Design + Impl\\\\ExampleProjects\\\\AlphaAlgTimedRC\\\\communicationC.rct\\", "\\test");

    public void inputLoop() {
        boolean close = false;
        while (!close) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input path of .rct file to transform or exit to close.");
            String inputFilePath = scanner.nextLine();
            if (inputFilePath.equals("exit")) {
                close = true;
                continue;
            }

            System.out.println("Input desired path for .xml file (file path ending in \\output.xml where 'output' is the desired name of the output file), or exit to close.");
            String outputFilePath = scanner.nextLine();
            if (outputFilePath.equals("exit")) {
                close = true;
                continue;
            }

            FileRead reader = new FileRead(inputFilePath, outputFilePath);
            ArrayList<String> inputData = reader.readInput();
            if (inputData.isEmpty()) {
                continue;
            }

            TransformEngine engine = new TransformEngine(inputData);
            //reader.write(engine.transform());

            //sout("File successfully transformed");
        }
    }
}
