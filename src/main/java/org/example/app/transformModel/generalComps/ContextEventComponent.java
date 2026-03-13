package org.example.app.transformModel.generalComps;

import org.example.app.transformModel.EventBox;
import org.example.app.transformModel.context.ContextData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ContextEventComponent extends NamedComponent {
    protected List<ContextData> context = new ArrayList<>();
    protected List<EventBox> eventBoxes = new ArrayList<>();

    public ContextEventComponent(int id, String name) {
        super(id, name);
    }

    public ContextEventComponent(int id, String name, int parentId) {
        super(id, name, parentId);
    }

    public List<ContextData> getContext() {
        return context;
    }

    public void addContextLine(ContextData contextLine) {
        if (context.contains(contextLine)) {
            throw new RuntimeException(
                    String.format("Cannot add context %s to component %s twice", contextLine.getName(), name));
        }
        context.add(contextLine);
    }

    public List<EventBox> getEventBoxes() {
        return eventBoxes;
    }

    public void addEventBox(EventBox eventBox) {
        if (eventBoxes.contains(eventBox)) {
            throw new RuntimeException(
                    String.format("Cannot add event box %s to component %s twice", eventBox.getName(), name));
        }
        eventBoxes.add(eventBox);
    }

    public EventBox getEventBoxWithName(String name) {
        for (EventBox box : eventBoxes) {
            if (box.getEventName().equals(name)) {
                return box;
            }
        }
        return null;
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof ContextData) {
            addContextLine((ContextData) child);
        } else if (child instanceof EventBox) {
            addEventBox((EventBox) child);
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
        List<NamedComponent> eventBoxList = new ArrayList<>(eventBoxes);
        children.put("context", contextList);
        children.put("eventBox", eventBoxList);
        return children;
    }
}
