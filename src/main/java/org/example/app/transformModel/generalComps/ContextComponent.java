package org.example.app.transformModel.generalComps;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;

import java.util.*;

/**
 * Class that represents the following RoboChart components: Enumeration, Record, RCInterface, Imports and Function.
 */
public class ContextComponent extends NameOnlyComponent {
    /**
     * List of ContextData instances that represents the component's context.
     */
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

    /**
     * Fetches all of the ContextData of type {@code ContextType.EVENT}.
     * @return List of ContextData containing all events from the component's {@code context}.
     */
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
                            "Cannot make component %s a child of component %s",
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
