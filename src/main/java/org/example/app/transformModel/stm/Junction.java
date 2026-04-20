package org.example.app.transformModel.stm;

import org.example.app.transformModel.generalComps.NamedComponent;

/**
 * Class that represents a RoboChart Junction.
 */
public class Junction extends NamedComponent {
    /**
     * JunctionType instance to represent the type of junction.
     */
    private JunctionType type;

    public Junction(int id, String name, int parentId, JunctionType type) {
        super(id, name, parentId);
        this.type = type;
    }

    public JunctionType getType() {
        return type;
    }

    @Override
    public void addChild(NamedComponent child) {
        throw new RuntimeException(
                String.format(
                        "Cannot make component %s a child of junction %s",
                        child.getName(),
                        name)
        );
    }
}
