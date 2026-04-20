package org.example.app.transformModel.connection;

import org.example.app.transformModel.generalComps.NamedComponent;

/**
 * Class that represents event boxes that Connections between components in RoboChart diagrams connect to and from.
 */
public class EventBox extends NamedComponent {
    /**
     * String representing the name of the event that this event box represents (without any type information).
     */
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
