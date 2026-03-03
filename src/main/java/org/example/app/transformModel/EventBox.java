package org.example.transformModel;

import org.example.transformModel.context.Event;
import org.example.transformModel.generalComps.NamedComponent;

public class EventBox extends NamedComponent {
    private Event event;

    public EventBox(String name, int parentId, Event event) {
        super(name, parentId);
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
