package org.example.app.transformModel.stm;

import org.example.app.transformModel.generalComps.ContextEventComponent;

public class StateMachine extends ContextEventComponent {
    private StmBody body;

    public StateMachine(int id, String name, int parentId) {
        super(id, name, parentId);
        body = new StmBody(id+1,"name", id); //TODO: StmBody name in StateMachine
    }

    public StateMachine(int id, String name) {
        super(id, name);
        body = new StmBody(id+1,"name", id); //TODO: StmBody name in StateMachine
    }

    public StmBody getBody() {
        return body;
    }
}
