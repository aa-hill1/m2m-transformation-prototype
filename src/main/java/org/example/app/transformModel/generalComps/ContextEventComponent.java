package org.example.app.transformModel.generalComps;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents components that can have context data and event boxes, for example robotic platforms.
 */
public class ContextEventComponent extends NamedComponent {
    /**
     * ComplexCompType that represents the type of component that the class represents.
     */
    protected ComplexCompType type;
    /**
     * List of ContextData instances that represents the component's context.
     */
    protected List<ContextData> context = new ArrayList<>();
    /**
     * List of EventBox instances that represents the component's events.
     */
    protected List<EventBox> eventBoxes = new ArrayList<>();

    public ContextEventComponent(int id, String name, ComplexCompType type) {
        super(id, name);
        this.type = type;
    }

    public ContextEventComponent(int id, String name, int parentId, ComplexCompType type) {
        super(id, name, parentId);
        this.type = type;
    }

    public ComplexCompType getType() {
        return type;
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

    /**
     * Fetches an event box using the {@code name} of the event it represents.
     * @param name
     * string representing the name of the event linked to the desired event box.
     * @return the desired EventBox instance.
     */
    public EventBox getEventBoxWithName(String name) {
        for (EventBox box : eventBoxes) {
            if (box.getEventName().equals(name)) {
                return box;
            }
        }
        throw new RuntimeException(
                String.format("Cannot get event box with name %s from component %s", name, this.name));
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
