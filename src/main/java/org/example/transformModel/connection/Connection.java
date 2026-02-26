package org.example.transformModel.connection;

import org.example.transformModel.generalComps.NamedComponent;

public class Connection  extends NamedComponent {
    protected NamedComponent src;
    protected NamedComponent tgt;

    public Connection(String name, int parentId, NamedComponent src, NamedComponent tgt) {
        super(name, parentId);
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
