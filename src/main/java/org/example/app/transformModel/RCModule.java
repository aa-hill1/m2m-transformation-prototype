package org.example.app.transformModel;

import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.StmComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RCModule extends NamedComponent {
    private List<Connection> connections = new ArrayList<>();
    private List<Controller> controllers = new ArrayList<>();
    private List<StmComponent> operations = new ArrayList<>();
    private List<ContextEventComponent> rps = new ArrayList<>();
    private List<StmComponent> stms = new ArrayList<>();

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

    public List<StmComponent> getOperations() {
        return operations;
    }
    public void addOperation(StmComponent operation) {
        if (operations.contains(operation)) {
            throw new RuntimeException(
                    String.format("Cannot add operation %s to module %s twice", operation.getName(), name));
        }
        operations.add(operation);
    }

    public List<ContextEventComponent> getRps() {
        return rps;
    }
    public void addRP(ContextEventComponent rp) {
        if (rps.contains(rp)) {
            throw new RuntimeException(
                    String.format("Cannot add RP %s to module %s twice", rp.getName(), name));
        }
        rps.add(rp);
    }

    public List<StmComponent> getStms() {
        return stms;
    }
    public void addSTM(StmComponent stm) {
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
        } else if (child instanceof StmComponent) {
            if (((StmComponent) child).getType() == ComplexCompType.OPERATION) {
                addOperation((StmComponent) child);
            } else if (((StmComponent) child).getType() == ComplexCompType.STM) {
                addSTM((StmComponent) child);
            }
        } else if (
                child instanceof ContextEventComponent
                        && ((ContextEventComponent) child).getType() == ComplexCompType.RP) {
            addRP((ContextEventComponent) child);
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

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = super.getChildren();
        List<NamedComponent> componentList = new ArrayList<>(controllers);
        componentList.addAll(operations);
        componentList.addAll(rps);
        componentList.addAll(stms);
        children.put("components", componentList);
        List<NamedComponent> connectionList = new ArrayList<>(connections);
        children.put("connections", connectionList);
        return children;
    }

    @Override
    public int getContainedComponentsCount() {
        int count = 0;
        for (List<? extends NamedComponent> compList : List.of(controllers, operations, rps, stms)) {
            for (NamedComponent comp : compList) {
                count += 1 + comp.getContainedComponentsCount();
            }
        }
        return count;
    }
}
