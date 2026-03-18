package org.example.app;

import org.example.app.transformModel.TransformModel;

import java.util.ArrayList;

public class TransformEngine {
    ArrayList<String> data;

    public TransformEngine(ArrayList<String> data) {
        this.data = data;
    }

    public void transform() {
        SrcParser parser = new SrcParser(data);
        TransformModel model = parser.parse();

        TgtTemplateGenerator tgtGen = new TgtTemplateGenerator(model);
        String mxGraphXml = tgtGen.translate();
    }
}
