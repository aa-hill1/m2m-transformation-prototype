package org.example.app.transformModel;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that stores the transformation model and enables search and lookup.
 */
public class TransformModel {
    /**
     * List of NamedComponents which have {@code parentID} of 1, so are the root components in the model.
     */
    private List<NamedComponent> rootComps = new ArrayList<>();
    /**
     * Map of model components to their {@code id}.
     */
    Map<Integer, NamedComponent> compIdMap = new HashMap<>();
    /**
     * Map of model components to their {@code name}, excluding context data nad event boxes as their names are not
     * unique.
     */
    Map<String, NamedComponent> compNameMap = new HashMap<>(); //Excludes context data and event boxes

    public TransformModel() {}

    /**
     * Finds the component in the model with ID: {@code id}.
     * @param id
     * integer representing the ID of the component to find.
     * @return NamedComponent instance representing the desired component.
     */
    public NamedComponent getCompById(int id) {
        NamedComponent foundComp = compIdMap.get(id);
        if (foundComp == null) {
            throw new RuntimeException(String.format(
                    "Error - please ensure components are defined in order of use, could not find component with ID %s",
                    id));
        }
        return foundComp;
    }

    /**
     * Finds the component in the model with name: {@code name}.
     * @param name
     * string representing the name of the component to find.
     * @return NamedComponent instance representing the desired component.
     */
    public NamedComponent getCompByName(String name) {
        NamedComponent foundComp = compNameMap.get(name);
        if (foundComp == null) {
            throw new RuntimeException(String.format(
                    "Error - please ensure components are defined in order of use, could not find component with name %s",
                    name));
        }
        return foundComp;
    }

    /**
     * Handles adding a new component to the transformation model.
     * @param component
     * NamedComponent instance to add to the model.
     */
    public void addNewComp(NamedComponent component) {
        if (component.getParentId() == 1) {
            rootComps.add(component);
        }
        if (!(component instanceof ContextData || component instanceof EventBox)) {
            compNameMap.put(component.getName(), component);
        }
        compIdMap.put(component.getId(), component);
    }

    /**
     * Adds several compononents of type {@code T} to the transformation model.
     * @param components
     * List of components of type {@code T} to add.
     * @param <T>
     * type of component to add to the transformation model.
     */
    public <T> void addNewComp(List<T> components) {
        for (T component : components) {
            addNewComp((NamedComponent) component);
        }
    }

    public List<NamedComponent> getRootComps() {
        return rootComps;
    }

    /**
     * Finds and returns an operation using its name.
     * @param nameToFind
     * string representing the name of the operation to find.
     * @return NamedComponent instance of the desired operation.
     */
    public NamedComponent getOpByName(String nameToFind) {
        for (String compName : compNameMap.keySet()) {
            if (compName.contains("(") && compName.contains(")")) {
                if (compName.substring(0, compName.indexOf('(')).equals(nameToFind)) {
                    return compNameMap.get(compName);
                }
            }
        }
        throw new RuntimeException("Error - cannot find operation with name " + nameToFind);
    }
}
