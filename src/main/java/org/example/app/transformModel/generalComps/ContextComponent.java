package org.example.transformModel.generalComps;

import org.example.transformModel.context.ContextData;

import java.util.ArrayList;
import java.util.List;

public class ContextComponent extends NameOnlyComponent {
    protected List<ContextData> context = new ArrayList<>();

    public ContextComponent(String name, SimpleCompType type) {
        super(name, type);
    }

    public ContextComponent(String name, int parentId, SimpleCompType type) {
        super(name, parentId, type);
        context = new ArrayList<ContextData>();
    }

    public List<ContextData> getContext() {
        return context;
    }

    public void addContextLine(ContextData contextLine) {
        if (context.contains(contextLine)) {
            throw new RuntimeException(String.format("Cannot add context %s to component %s twice", contextLine.getName(), name));
        }
        context.add(contextLine);
    }
}
