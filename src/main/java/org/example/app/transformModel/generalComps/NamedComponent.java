package org.example.app.transformModel.generalComps;

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
}
