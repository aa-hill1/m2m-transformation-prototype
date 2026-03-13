package org.example.app.transformModel.generalComps;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;

import java.util.*;

public class ContextComponent extends NameOnlyComponent {
    protected List<ContextData> context = new ArrayList<>();

    public ContextComponent(int id, String name, SimpleCompType type) {
        super(id, name, type);
    }

    public ContextComponent(int id, String name, int parentId, SimpleCompType type) {
        super(id, name, parentId, type);
        context = new ArrayList<ContextData>();
    }

    public List<ContextData> getContext() {
        return context;
    }

    public List<ContextData> getEvents() {
        List<ContextData> events = new ArrayList<>();
        for (ContextData contextLine : context) {
            if (contextLine.getType() == ContextType.EVENT) {
                events.add(contextLine);
            }
        }
        return events;
    }

    public void addContextLine(ContextData contextLine) {
        if (context.contains(contextLine)) {
            throw new RuntimeException(
                    String.format(
                            "Cannot add context %s to component %s twice",
                            contextLine.getName(),
                            name));
        }
        context.add(contextLine);
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof ContextData) {
            addContextLine((ContextData) child);
        } else {
            throw new RuntimeException(
                    String.format(
                            "Cannot make component %s a child of contextComponent %s",
                            child.getName(),
                            name)
            );
        }
    }

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = new HashMap<>();
        List<NamedComponent> contextList = new ArrayList<>(context);
        children.put("context", contextList);
        return children;
    }
}
