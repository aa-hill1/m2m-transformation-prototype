package org.example.app.transformModel;

import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformModel {
    private List<NamedComponent> rootComps = new ArrayList<>();
    Map<Integer, NamedComponent> compIdMap = new HashMap<>();
    Map<String, NamedComponent> compNameMap = new HashMap<>();

    public TransformModel() {}

    public NamedComponent getCompById(int id) {
        return compIdMap.get(id);
    }

    public NamedComponent getCompByName(String name) {
        return compNameMap.get(name);
    }

    public void addNewComp(NamedComponent component) {
        if (component.getParentId() == 1) {
            rootComps.add(component);
        }
        compIdMap.put(component.getId(), component);
        compNameMap.put(component.getName(), component);
    }

    public List<NamedComponent> getRootComps() {
        return rootComps;
    }
}
