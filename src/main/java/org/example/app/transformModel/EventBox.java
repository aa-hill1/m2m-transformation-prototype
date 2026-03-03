package org.example.app.transformModel;

import org.example.app.transformModel.context.Event;
import org.example.app.transformModel.generalComps.NamedComponent;

public class EventBox extends NamedComponent {
    private Event event;

    public EventBox(int id, String name, int parentId, Event event) {
        super(id, name, parentId);
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
