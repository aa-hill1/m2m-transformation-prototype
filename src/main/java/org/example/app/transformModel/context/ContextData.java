package org.example.app.transformModel.context;

import org.example.app.transformModel.generalComps.NamedComponent;

public class ContextData extends NamedComponent {
    protected ContextType type;

    public ContextData(int id, String name, int parentId, ContextType type) {
        super(id, name, parentId);
        this.type = type;
    }

    public ContextType getType() {
        return type;
    }

    @Override
    public void addChild(NamedComponent child) {
        throw new RuntimeException(
                String.format(
                        "Cannot make component %s a child of contextData %s",
                        child.getName(),
                        name));
    }
}
