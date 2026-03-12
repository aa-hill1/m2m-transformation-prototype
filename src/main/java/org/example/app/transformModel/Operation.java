package org.example.app.transformModel;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.ContextComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.SimpleCompType;
import org.example.app.transformModel.stm.StmBody;

public class Operation extends ContextComponent {
    private StmBody stateMachine;

    public Operation(int id, String name) {
        super(id, name, SimpleCompType.OPERATION);
        stateMachine = new StmBody(id+1, (name+"-stmBody"), id);
    }

    public Operation(int id, String name, int parentId) {
        super(id, name, parentId, SimpleCompType.OPERATION);
        stateMachine = new StmBody(id+1, (name+"-stmBody"), id);
    }

    public StmBody getStmBody() {
        return stateMachine;
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof ContextData) {
            super.addChild(child);
        } else {
            stateMachine.addChild(child);
        }
    }
}
