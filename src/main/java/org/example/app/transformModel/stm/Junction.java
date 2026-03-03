package org.example.app.transformModel.stm;

import org.example.app.transformModel.generalComps.NamedComponent;

public class Junction extends NamedComponent {
    private JunctionType type;

    public Junction(int id, String name, int parentId, JunctionType type) {
        super(id, name, parentId);
        this.type = type;
    }

    public JunctionType getType() {
        return type;
    }
}
