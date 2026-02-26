package org.example.transformModel.stm;

import org.example.transformModel.generalComps.NamedComponent;

public class State extends NamedComponent {
    private boolean finalState = false;
    private String entryAction = null;
    private String duringAction = null;
    private String exitAction = null;

    public State(String name, int parentId) {
        super(name, parentId);
        finalState = true;
    }

    public State(String name, int parentId, String entryAction, String duringAction, String exitAction) {
        super(name, parentId);
        this.entryAction = entryAction;
        this.duringAction = duringAction;
        this.exitAction = exitAction;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public String getEntryAction() {
        return entryAction;
    }

    public String getDuringAction() {
        return duringAction;
    }

    public String getExitAction() {
        return exitAction;
    }
}