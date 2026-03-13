package org.example.app.transformModel;

import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.stm.StateMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller extends ContextEventComponent {
    private List<Connection> connections = new ArrayList<>();
    private List<StateMachine> stms = new ArrayList<>();
    private List<Reference> refs = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();

    public Controller(int id, String name) {
        super(id, name);
    }

    public Controller(int id, String name, int parentId) {
        super(id, name, parentId);
    }

    public List<Connection> getConnections() {
        return connections;
    }
    public void addConnection(Connection connection) {
        if (connections.contains(connection)) {
            throw new RuntimeException(
                    String.format("Cannot add connection %s to controller %s twice", connection.getName(), name));
        }
        connections.add(connection);
    }

    public List<StateMachine> getStms() {
        return stms;
    }
    public void addStm(StateMachine stm) {
        if (stms.contains(stm)) {
            throw new RuntimeException(
                    String.format("Cannot add STM %s to controller %s twice", stm.getName(), name));
        }
        stms.add(stm);
    }

    public List<Reference> getRefs() {
        return refs;
    }
    public void addRef(Reference ref) {
        if (refs.contains(ref)) {
            throw new RuntimeException(
                    String.format("Cannot add reference %s to controller %s twice", ref.getName(), name));
        }
        refs.add(ref);
    }

    public List<Operation> getOperations() {
        return operations;
    }
    public void addOperation(Operation operation) {
        if (operations.contains(operation)) {
            throw new RuntimeException(
                    String.format("Cannot add operation %s to controller %s twice", operation.getName(), name));
        }
        operations.add(operation);
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof ContextData || child instanceof EventBox){
            super.addChild(child);
        } else if (child instanceof Connection) {
            addConnection((Connection) child);
        } else if (child instanceof StateMachine) {
            addStm((StateMachine) child);
        } else if (child instanceof Reference) {
            addRef((Reference) child);
        } else if (child instanceof Operation) {
            addOperation((Operation) child);
        } else {
            throw new RuntimeException(
                    String.format(
                            "Cannot make component %s a child of controller %s",
                            child.getName(),
                            name
                    )
            );
        }
    }

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = super.getChildren();
        List<NamedComponent> componentList = new ArrayList<>(stms);
        componentList.addAll(refs);
        componentList.addAll(operations);
        children.put("components", componentList);
        List<NamedComponent> connectionList = new ArrayList<>(connections);
        children.put("connections", connectionList);
        return children;
    }

    @Override
    public int getContainedComponentsCount() {
        int count = refs.size();
        for (StateMachine stm : stms) {
            count += 1 + stm.getContainedComponentsCount();
        }
        for (Operation op : operations) {
            count += 1 + op.getContainedComponentsCount();
        }
        return count;
    }
}
