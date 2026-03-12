package org.example.app.transformModel;

import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.stm.StateMachine;

import java.util.ArrayList;
import java.util.List;

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
            throw new RuntimeException(String.format("Cannot add connection %s to controller %s twice", connection.getName(), name));
        }
        connections.add(connection);
    }

    public List<StateMachine> getStms() {
        return stms;
    }
    public void addStm(StateMachine stm) {
        if (stms.contains(stm)) {
            throw new RuntimeException(String.format("Cannot add STM %s to controller %s twice", stm.getName(), name));
        }
        stms.add(stm);
    }

    public List<Reference> getRefs() {
        return refs;
    }
    public void addRef(Reference ref) {
        if (refs.contains(ref)) {
            throw new RuntimeException(String.format("Cannot add reference %s to controller %s twice", ref.getName(), name));
        }
        refs.add(ref);
    }

    public List<Operation> getOperations() {
        return operations;
    }
    public void addOperation(Operation operation) {
        if (operations.contains(operation)) {
            throw new RuntimeException(String.format("Cannot add operation %s to controller %s twice", operation.getName(), name));
        }
        operations.add(operation);
    }
}
