package org.example.app.transformModel.generalComps;

import java.util.List;
import java.util.Map;

public abstract class NamedComponent {
    protected int id;
    protected String name;
    protected int parentId = 1;

    public NamedComponent(int id, String name) {
        this.name = name;
    }

    public NamedComponent(int id, String name, int parentId) {
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

    public abstract void addChild(NamedComponent child);

    public Map<String, List<NamedComponent>> getChildren() {
        return null;
    }

    public int getContainedComponentsCount() {
        return 0;
    };
}
