package org.example.app.transformModel;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.NamedComponent;

public class EventBox extends NamedComponent {
    private ContextData event;

    public EventBox(int id, String name, int parentId, ContextData event) {
        super(id, event.getName(), parentId);
        if (event.getType() != ContextType.EVENT) {
            throw new RuntimeException("Event box" + name +
                    " cannot reference "+ event.getName() +" of type " + event.getType());
        }
        this.event = event;
    }

    public ContextData getEvent() {
        return event;
    }

    public String getEventName() {
        return name.split(" ")[0];
    }

    @Override
    public void addChild(NamedComponent child) {
        throw new RuntimeException(
                String.format(
                        "Cannot make component %s a child of eventBox %s",
                        child.getName(),
                        name)
        );
    }
}
