package org.example.app.transformModel.generalComps;

import org.example.app.transformModel.EventBox;
import org.example.app.transformModel.context.ContextData;

import java.util.ArrayList;
import java.util.List;

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
            throw new RuntimeException(String.format("Cannot add context %s to component %s twice", contextLine.getName(), name));
        }
        context.add(contextLine);
    }

    public List<EventBox> getEventBoxes() {
        return eventBoxes;
    }

    public void addEventBox(EventBox eventBox) {
        if (eventBoxes.contains(eventBox)) {
            throw new RuntimeException(String.format("Cannot add event box %s to component %s twice", eventBox.getName(), name));
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
}
