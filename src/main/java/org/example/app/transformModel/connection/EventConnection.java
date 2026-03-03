package org.example.app.transformModel.connection;

import org.example.app.transformModel.EventBox;
import org.example.app.transformModel.generalComps.NamedComponent;

public class EventConnection extends Connection {
    private boolean bidi = false;

    public EventConnection(int id, String name, int parentId, NamedComponent src, NamedComponent tgt) {
        super(id, name, parentId, src, tgt);
        if (((EventBox) src).getEvent().isBroadcast() || ((EventBox) tgt).getEvent().isBroadcast()) {
            bidi = true;
        }
    }

    public boolean isBidi() {
        return bidi;
    }
}
