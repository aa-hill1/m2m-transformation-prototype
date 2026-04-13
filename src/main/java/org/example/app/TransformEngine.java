package org.example.app;

import org.example.app.src.SrcParser;
import org.example.app.tgt.TgtGenerator;
import org.example.app.transformModel.TransformModel;

import java.util.ArrayList;

/**
 * Class that coordinates the M2M transformation
 */
public class TransformEngine {
    /**
     * ArrayList of strings representing the input data from the .rct file.
     */
    ArrayList<String> data;

    public TransformEngine(ArrayList<String> data) {
        this.data = data;
    }

    /**
     * Coordinates, and returns the results of, the M2M transformation.
     * @return
     * string containing the XML of the transformed diagram.
     */
    public String transform() {
        SrcParser parser = new SrcParser(data);
        TransformModel model = parser.parse();

        TgtGenerator tgtGen = new TgtGenerator(model);
        return tgtGen.translate();
    }
}
