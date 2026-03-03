package org.example.app.transformModel.generalComps;

public class NameOnlyComponent extends NamedComponent {
    protected SimpleCompType type;

    public NameOnlyComponent(int id, String name, SimpleCompType type) {
        super(id, name);
        this.type = type;
    }

    public NameOnlyComponent(int id, String name, int parentId, SimpleCompType type) {
        super(id, name, parentId);
        this.type = type;
    }

    public SimpleCompType getType() {
        return type;
    }
}
