package org.example.app.transformModel.connection;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.NamedComponent;

public class EventBox extends NamedComponent {
    private String eventName;

    public EventBox(int id, String name, int parentId) {
        super(id, name, parentId);
        this.eventName = name.split(" ")[0].replace(":", "");
    }

    public String getEventName() {
        return eventName;
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
