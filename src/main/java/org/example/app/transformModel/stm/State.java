package org.example.app.transformModel.stm;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.StmComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class represents RoboChart States and Final States.
 */
public class State extends StmComponent {
    /**
     * boolean determining whether the state is a final state or not.
     */
    private boolean finalState;

    public State(int id, String name, int parentId, boolean finalState) {
        super(id, name, parentId, ComplexCompType.STATE);
        this.finalState = finalState;
        if (!finalState) {
            body = new StmBody(id+1, (name+"-stmBody"), id);
        } else {
            body = null;
            context = null;
        }
        eventBoxes = null;
    }

    public boolean isFinalState() {
        return finalState;
    }

    @Override
    public void addContextLine(ContextData contextLine) {
        if (finalState) {
            throw new RuntimeException(
                    String.format("Cannot add context %s to final state %s", contextLine.getName(), name));
        }
        if (contextLine.getType() != ContextType.TEXT) {
            throw new RuntimeException(
                    String.format(
                            "Cannot add context %s to %s, must an Action of type ContextType.TEXT",
                            contextLine.getName(),
                            name));
        }
        super.addContextLine(contextLine);
    }

    @Override
    public EventBox getEventBoxWithName(String name) {
        throw new RuntimeException("Cannot get event box from state " + name);
    }

    @Override
    public void addEventBox(EventBox eventBox) {
        throw new RuntimeException("Cannot add event box to component of type state");
    }

    @Override
    public void addChild(NamedComponent child) {
        if (finalState || child instanceof EventBox) {
            throw new RuntimeException(
                    String.format(
                    "Cannot make component %s a child of final state %s",
                    child.getName(),
                    name)
            );
        } else if (child instanceof ContextData) {
            addContextLine((ContextData) child);
        } else {
            body.addChild(child);
        }
    }

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        if (finalState) {
            return null;
        }
        Map<String, List<NamedComponent>> children = new HashMap<>();
        List<NamedComponent> contextList = new ArrayList<>(context);
        children.put("context", contextList);
        children.put("components", List.of(body));
        return children;
    }

    @Override
    public int getContainedComponentsCount() {
        if (finalState) {
            return 0;
        }
        return super.getContainedComponentsCount();
    }
}