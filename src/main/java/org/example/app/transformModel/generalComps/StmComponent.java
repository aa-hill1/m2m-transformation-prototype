package org.example.app.transformModel.generalComps;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.stm.StmBody;

import java.util.List;
import java.util.Map;

public class StmComponent extends ContextEventComponent {
    protected StmBody body;

    public StmComponent(int id, String name, ComplexCompType type) {
        super(id, name, type);
        body = new StmBody(id+1,(name+"-stmBody"), id);
    }

    public StmComponent(int id, String name, int parentId, ComplexCompType type) {
        super(id, name, parentId, type);
        body = new StmBody(id+1,(name+"-stmBody"), id);
    }

    public StmBody getBody() {
        return body;
    }

    public ComplexCompType getType() {
        return type;
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
