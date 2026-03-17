package org.example.app.transformModel;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformModel { //TODO: Javadoc
    private List<NamedComponent> rootComps = new ArrayList<>();
    Map<Integer, NamedComponent> compIdMap = new HashMap<>();
    Map<String, NamedComponent> compNameMap = new HashMap<>(); //Excludes context data and event boxes

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
        if (!(component instanceof ContextData || component instanceof EventBox)) {
            compNameMap.put(component.getName(), component);
        }
        compIdMap.put(component.getId(), component);
    }

    public <T> void addNewComp(List<T> components) {
        for (T component : components) {
            addNewComp((NamedComponent) component);
        }
    }

    public List<NamedComponent> getRootComps() {
        return rootComps;
    }
}
