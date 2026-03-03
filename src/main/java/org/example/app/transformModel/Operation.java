package org.example.app.transformModel;

import org.example.app.transformModel.generalComps.ContextComponent;
import org.example.app.transformModel.generalComps.SimpleCompType;
import org.example.app.transformModel.stm.StmBody;

public class Operation extends ContextComponent {
    private StmBody stateMachine;

    public Operation(int id, String name) {
        super(id, name, SimpleCompType.OPERATION);
        stateMachine = new StmBody(id+1, "name", id); //TODO: StmBody name in Operation
    }

    public Operation(int id, String name, int parentId) {
        super(id, name, parentId, SimpleCompType.OPERATION);
        stateMachine = new StmBody(id+1, "name", id); //TODO: StmBody name in Operation
    }

    public StmBody getStateMachine() {
        return stateMachine;
    }
}
