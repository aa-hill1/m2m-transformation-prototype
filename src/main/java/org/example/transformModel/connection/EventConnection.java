package org.example.transformModel.connection;

import org.example.transformModel.EventBox;
import org.example.transformModel.generalComps.NamedComponent;

public class EventConnection extends Connection {
    private boolean bidi = false;

    public EventConnection(String name, int parentId, NamedComponent src, NamedComponent tgt) {
        super(name, parentId, src, tgt);
        if (((EventBox) src).getEvent().isBroadcast() || ((EventBox) tgt).getEvent().isBroadcast()) {
            bidi = true;
        }
    }

    public boolean isBidi() {
        return bidi;
    }
}
