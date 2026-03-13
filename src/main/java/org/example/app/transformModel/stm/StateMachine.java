package org.example.app.transformModel.stm;

import org.example.app.transformModel.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.List;
import java.util.Map;

public class StateMachine extends ContextEventComponent {
    private StmBody body;

    public StateMachine(int id, String name, int parentId) {
        super(id, name, parentId);
        body = new StmBody(id+1,(name+"-stmBody"), id);
    }

    public StateMachine(int id, String name) {
        super(id, name);
        body = new StmBody(id+1,(name+"-stmBody"), id);
    }

    public StmBody getStmBody() {
        return body;
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof ContextData || child instanceof EventBox) {
            super.addChild(child);
        } else {
            body.addChild(child);
        }
    }

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = super.getChildren();
        children.put("components", List.of(body));
        return children;
    }

    @Override
    public int getContainedComponentsCount() {
        return body.getContainedComponentsCount();
    }
}
