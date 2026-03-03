package org.example.app.transformModel.stm;

import org.example.app.transformModel.generalComps.NamedComponent;

public class State extends NamedComponent {
    private boolean finalState;
    private String entryAction = null;
    private String duringAction = null;
    private String exitAction = null;

    public State(int id, String name, int parentId, boolean finalState) {
        super(id, name, parentId);
        this.finalState = finalState;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public String getEntryAction() {
        return entryAction;
    }
    public void setEntryAction(String entryAction) {
        this.entryAction = entryAction;
    }

    public String getDuringAction() {
        return duringAction;
    }
    public void setDuringAction(String duringAction) {
        this.duringAction = duringAction;
    }

    public String getExitAction() {
        return exitAction;
    }
    public void setExitAction(String exitAction) {
        this.exitAction = exitAction;
    }
}