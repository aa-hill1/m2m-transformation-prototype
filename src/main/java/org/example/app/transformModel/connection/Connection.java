package org.example.app.transformModel.connection;

import org.example.app.transformModel.generalComps.NamedComponent;

public class Connection  extends NamedComponent {
    protected NamedComponent src;
    protected NamedComponent tgt;

    public Connection(int id, String name, int parentId, NamedComponent src, NamedComponent tgt) {
        super(id, name, parentId);
        this.src = src;
        this.tgt = tgt;
    }

    public NamedComponent getSrc() {
        return src;
    }

    public NamedComponent getTgt() {
        return tgt;
    }
}
