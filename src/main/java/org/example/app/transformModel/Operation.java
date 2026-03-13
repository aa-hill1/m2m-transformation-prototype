package org.example.app.transformModel;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.ContextComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.SimpleCompType;
import org.example.app.transformModel.stm.StmBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = new HashMap<>();
        children.put("components", List.of(stateMachine));
        return children;
    }

    @Override
    public int getContainedComponentsCount() {
        return stateMachine.getContainedComponentsCount();
    }
}
