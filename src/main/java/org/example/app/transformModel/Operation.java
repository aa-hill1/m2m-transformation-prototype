package org.example.transformModel;

import org.example.transformModel.generalComps.ContextComponent;
import org.example.transformModel.generalComps.SimpleCompType;
import org.example.transformModel.stm.StmBody;

public class Operation extends ContextComponent {
    private StmBody stateMachine;

    public Operation(String name, SimpleCompType type) {
        super(name, type);
        stateMachine = new StmBody("name", id); //TODO: StmBody name in Operation
    }

    public Operation(String name, int parentId, SimpleCompType type) {
        super(name, parentId, type);
        stateMachine = new StmBody("name", id); //TODO: StmBody name in Operation
    }

    public StmBody getStateMachine() {
        return stateMachine;
    }
}
