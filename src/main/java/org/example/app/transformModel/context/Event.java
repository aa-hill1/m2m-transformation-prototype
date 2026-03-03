package org.example.app.transformModel.context;

public class Event extends ContextData {
    private boolean broadcast;

    public Event(int id, String name, int parentId, boolean broadcast) {
        super(id, name, parentId, ContextType.EVENT);
        this.broadcast = broadcast;
    }

    public boolean isBroadcast() {
        return broadcast;
    }
}
