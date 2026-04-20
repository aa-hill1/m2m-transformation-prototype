package org.example.app.transformModel.stm;

import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.generalComps.NamedComponent;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents the state diagram within StmComponent and State instances.
 */
public class StmBody extends NamedComponent {
    /**
     * List of State instances within the diagram.
     */
    private List<State> states = new ArrayList<>();
    /**
     * List of Junction instances within the diagram.
     */
    private List<Junction> junctions = new ArrayList<>();
    /**
     * List of Connection instances within the diagram.
     */
    private List<Connection> transitions = new ArrayList<>();

    public StmBody(int id, String name, int parentId) {
        super(id, name, parentId);
    }

    public List<State> getStates() {
        return states;
    }

    public void addState(State state) {
        if (states.contains(state)) {
            throw new RuntimeException(
                    String.format("Cannot add state %s to stmBody %s twice", state.getName(), name));
        }
        states.add(state);
    }

    public List<Junction> getJunctions() {
        return junctions;
    }

    public void addJunction(Junction junction) {
        if (junctions.contains(junction)) {
            throw new RuntimeException(
                    String.format("Cannot add junction %s to stmBody %s twice", junction.getName(), name));
        }
        junctions.add(junction);
    }

    public List<Connection> getTransitions() {
        return transitions;
    }

    public void addTransition(Connection transition) {
        if (transitions.contains(transition)) {
            throw new RuntimeException(
                    String.format("Cannot add transition %s to stmBody %s twice", transition.getName(), name));
        }
        transitions.add(transition);
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof State) {
            addState((State) child);
        } else if (child instanceof Junction) {
            addJunction((Junction) child);
        } else if (child instanceof Connection) {
            addTransition((Connection) child);
        } else {
            throw new RuntimeException(
                    String.format(
                            "Cannot make component %s a child of stmBody %s",
                            child.getName(),
                            name)
            );
        }
    }

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = new HashMap<>();
        List<NamedComponent> componentList = new ArrayList<>(states);
        componentList.addAll(junctions);
        List<NamedComponent> transitionList = new ArrayList<>(transitions);
        children.put("components", componentList);
        children.put("connections", transitionList);
        return children;
    }

    @Override
    public int getContainedComponentsCount() {
        int count = junctions.size();
        for (State state : states) {
            count += 1 + state.getContainedComponentsCount();
        }
        return count;
    }
}
