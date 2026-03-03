package org.example.transformModel.stm;

import org.example.transformModel.generalComps.ContextEventComponent;

public class StateMachine extends ContextEventComponent {
    private StmBody body;

    public StateMachine(String name, int parentId) {
        super(name, parentId);
        body = new StmBody("name", id); //TODO: StmBody name in StateMachine
    }

    public StateMachine(String name) {
        super(name);
        body = new StmBody("name", id); //TODO: StmBody name in StateMachine
    }

    public StmBody getBody() {
        return body;
    }
}
