package org.example.app;

import java.util.ArrayList;

public class TransformEngine {
    ArrayList<String> data;

    public TransformEngine(ArrayList<String> data) {
        this.data = data;
    }

    public void transform() {
        SrcParser parser = new SrcParser(data);
    }
}
