package org.example.transformModel.generalComps;

public class NameOnlyComponent extends NamedComponent {
    protected SimpleCompType type;

    public NameOnlyComponent(String name, SimpleCompType type) {
        super(name);
        this.type = type;
    }

    public NameOnlyComponent(String name, int parentId, SimpleCompType type) {
        super(name, parentId);
        this.type = type;
    }

    public SimpleCompType getType() {
        return type;
    }
}
