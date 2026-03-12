package org.example.app.transformModel;

import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.stm.StateMachine;

import java.util.ArrayList;
import java.util.List;

public class RCModule extends NamedComponent {
    private List<Connection> connections = new ArrayList<>();
    private List<Controller> controllers = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();
    private List<RoboticPlatform> rps = new ArrayList<>();
    private List<StateMachine> stms = new ArrayList<>();

    public RCModule(int id, String name) {
        super(id, name);
    }

    public List<Connection> getConnections() {
        return connections;
    }
    public void addConnection(Connection connection) {
        if (connections.contains(connection)) {
            throw new RuntimeException(
                    String.format("Cannot add connection %s to module %s twice", connection.getName(), name));
        }
        connections.add(connection);
    }

    public List<Controller> getControllers() {
        return controllers;
    }
    public void addController(Controller controller) {
        if (controllers.contains(controller)) {
            throw new RuntimeException(
                    String.format("Cannot add controller %s to module %s twice", controller.getName(), name));
        }
        controllers.add(controller);
    }

    public List<Operation> getOperations() {
        return operations;
    }
    public void addOperation(Operation operation) {
        if (operations.contains(operation)) {
            throw new RuntimeException(
                    String.format("Cannot add operation %s to module %s twice", operation.getName(), name));
        }
        operations.add(operation);
    }

    public List<RoboticPlatform> getRps() {
        return rps;
    }
    public void addRP(RoboticPlatform rp) {
        if (rps.contains(rp)) {
            throw new RuntimeException(
                    String.format("Cannot add RP %s to module %s twice", rp.getName(), name));
        }
        rps.add(rp);
    }

    public List<StateMachine> getStms() {
        return stms;
    }
    public void addSTM(StateMachine stm) {
        if (stms.contains(stm)) {
            throw new RuntimeException(
                    String.format("Cannot add STM %s to module %s twice", stm.getName(), name));
        }
        stms.add(stm);
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof Connection) {
            addConnection((Connection) child);
        } else if (child instanceof Controller) {
            addController((Controller) child);
        } else if (child instanceof Operation) {
            addOperation((Operation) child);
        } else if (child instanceof RoboticPlatform) {
            addRP((RoboticPlatform) child);
        } else if (child instanceof StateMachine) {
            addSTM((StateMachine) child);
        } else {
            throw new RuntimeException(
                    String.format(
                            "Cannot make component %s a child of RCModule %s",
                            child.getName(),
                            name
                    )
            );
        }
    }
}
