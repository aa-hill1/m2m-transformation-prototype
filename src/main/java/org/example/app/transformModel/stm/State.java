package org.example.app.transformModel.stm;

import org.example.app.transformModel.generalComps.NamedComponent;

public class State extends NamedComponent {
    private boolean finalState;
    private String entryAction = null;
    private String duringAction = null;
    private String exitAction = null;
    private StmBody stateMachine;

    public State(int id, String name, int parentId, boolean finalState) {
        super(id, name, parentId);
        this.finalState = finalState;
        if (!finalState) {
            stateMachine = new StmBody(id+1, (name+"-stmBody"), id);
        } else {
            stateMachine = null;
        }
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

    public StmBody getStmBody() {
        return stateMachine;
    }

    @Override
    public void addChild(NamedComponent child) {
        if (finalState) {
            throw new RuntimeException(
                    String.format(
                    "Cannot make component %s a child of final state %s",
                    child.getName(),
                    name)
            );
        }
        stateMachine.addChild(child);
    }
}