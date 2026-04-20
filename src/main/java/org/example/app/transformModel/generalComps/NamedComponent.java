package org.example.app.transformModel.generalComps;

import java.util.List;
import java.util.Map;

/**
 * General parent class representing a component in the transformation model.
 */
public abstract class NamedComponent {
    /**
     * int representing the unique ID of the component.
     */
    protected int id;
    /**
     * String representing the component's name.
     */
    protected String name;
    /**
     * int representing the ID of the component which contains this component, default value is 1.
     */
    protected int parentId = 1;

    public NamedComponent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public NamedComponent(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    /**
     * Method that adds a child to the component.
     * @param child
     * NamedComponent instance to add.
     */
    public abstract void addChild(NamedComponent child);

    /**
     * Method that returns a map of the children of the component. Where children are grouped in the map under the keys:
     * 'components', 'connections', 'context' and 'eventBox'.
     * @return null
     */
    public Map<String, List<NamedComponent>> getChildren() {
        return null;
    }

    /**
     * Returns the number of child components contained within this component (excluding event boxes, connections
     * and context data), to aid with target component spacing.
     * @return integer representing the number of components contained within this component.
     */
    public int getContainedComponentsCount() {
        return 0;
    };
}
