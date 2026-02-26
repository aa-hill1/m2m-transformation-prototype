package org.example.transformModel.stm;

import org.example.transformModel.generalComps.NamedComponent;

public class Junction extends NamedComponent {
    private JunctionType type;

    public Junction(String name, int parentId, JunctionType type) {
        super(name, parentId);
        this.type = type;
    }

    public JunctionType getType() {
        return type;
    }
}
