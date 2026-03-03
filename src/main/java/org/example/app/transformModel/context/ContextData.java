package org.example.transformModel.context;

import org.example.transformModel.generalComps.NamedComponent;

public class ContextData extends NamedComponent {
    protected ContextType type;

    public ContextData(String name, int parentId, ContextType type) {
        super(name, parentId);
        this.type = type;
    }

    public ContextType getType() {
        return type;
    }
}
