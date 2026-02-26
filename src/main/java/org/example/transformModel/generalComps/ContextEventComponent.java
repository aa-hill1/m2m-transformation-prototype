package org.example.transformModel.generalComps;

import org.example.transformModel.EventBox;
import org.example.transformModel.context.ContextData;

import java.util.ArrayList;
import java.util.List;

public class ContextEventComponent extends NamedComponent {
    protected List<ContextData> context;
    protected List<EventBox> eventBoxes;

    public ContextEventComponent(String name) {
        super(name);
        context = new ArrayList<>();
    }

    public ContextEventComponent(String name, int parentId) {
        super(name, parentId);
        eventBoxes = new ArrayList<>();
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

    public List<EventBox> getEventBoxes() {
        return eventBoxes;
    }

    public void addEventBox(EventBox eventBox) {
        if (eventBoxes.contains(eventBox)) {
            throw new RuntimeException(String.format("Cannot add context %s to component %s twice", eventBox.getName(), name));
        }
        eventBoxes.add(eventBox);
    }
}
