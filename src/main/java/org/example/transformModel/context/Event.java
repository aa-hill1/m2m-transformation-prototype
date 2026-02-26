package org.example.transformModel.context;

public class Event extends ContextData {
    private boolean broadcast;

    public Event(String name, int parentId, boolean broadcast) {
        super(name, parentId, ContextType.EVENT);
        this.broadcast = broadcast;
    }

    public boolean isBroadcast() {
        return broadcast;
    }
}
