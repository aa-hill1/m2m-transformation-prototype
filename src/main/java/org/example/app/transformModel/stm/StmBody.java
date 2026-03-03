package org.example.app.transformModel.stm;

import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.List;

public class StmBody extends NamedComponent {
    private List<State> states = new ArrayList<>();
    private List<Junction> junctions = new ArrayList<>();
    private List<Connection> transitions = new ArrayList<>();

    public StmBody(int id, String name, int parentId) {
        super(id, name, parentId);
    }

    public List<State> getStates() {
        return states;
    }

    public void addState(State state) {
        if (states.contains(state)) {
            throw new RuntimeException(String.format("Cannot add state %s to stmBody %s twice", state.getName(), name));
        }
        states.add(state);
    }

    public List<Junction> getJunctions() {
        return junctions;
    }

    public void addJunction(Junction junction) {
        if (junctions.contains(junction)) {
            throw new RuntimeException(String.format("Cannot add junction %s to stmBody %s twice", junction.getName(), name));
        }
        junctions.add(junction);
    }

    public List<Connection> getTransitions() {
        return transitions;
    }

    public void addTransition(Connection transition) {
        if (transitions.contains(transition)) {
            throw new RuntimeException(String.format("Cannot add transition %s to stmBody %s twice", transition.getName(), name));
        }
        transitions.add(transition);
    }
}
