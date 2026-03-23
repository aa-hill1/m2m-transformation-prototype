package org.example.app;

import org.example.app.src.SrcParser;
import org.example.app.tgt.TgtGenerator;
import org.example.app.transformModel.TransformModel;

import java.util.ArrayList;

public class TransformEngine {
    ArrayList<String> data;

    public TransformEngine(ArrayList<String> data) {
        this.data = data;
    }

    public String transform() {
        SrcParser parser = new SrcParser(data);
        TransformModel model = parser.parse();

        TgtGenerator tgtGen = new TgtGenerator(model);
        return tgtGen.translate();
    }
}
