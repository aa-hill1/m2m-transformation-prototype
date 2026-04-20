package org.example.app.transformModel.generalComps;

/**
 * Class that represents the following RoboChart components: RCPackages and primitive type declarations.
 */
public class NameOnlyComponent extends NamedComponent {
    /**
     * Either {@code SimpleCompType.RCPACKAGE} or {@code SimpleCompType.TYPE_DEC}.
     */
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

    @Override
    public void addChild(NamedComponent child) {
        throw new RuntimeException(
                String.format(
                        "Cannot make component %s a child of component %s",
                        child.getName(),
                        name)
        );
    }
}
