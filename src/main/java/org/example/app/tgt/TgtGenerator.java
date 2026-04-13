package org.example.app.tgt;

import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.TransformModel;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.State;
import org.example.app.transformModel.stm.StmBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class TgtGenerator {
    private TransformModel model;
    private CellFactory factory;
    private StringBuilder data;
    private Stack<String[]> coordStack;
    private Stack<Integer> childStack;
    private String[] coords;

    public TgtGenerator(TransformModel model) {
        this.model = model;
        factory = new CellFactory();
        data = new StringBuilder();
        coordStack = new Stack<>();
        childStack = new Stack<>();
        coords = new String[]{"0", "0"};
    }

    private void pushToStacks(int childCount) {
        if (!childStack.isEmpty()) {
            coordStack.push(coords);
            coords = new String[]{"0", "0"};
        }
        childStack.push(childCount);
    }

    private void updateStacksAfterTransform(int offset) {
        updateCoords(offset);
        decrementChildStack();
    }
    private void updateCoords(int offset) {
        int x = Integer.parseInt(coords[0]);
        x += offset * 160;
        if (x > 3300) {
            x %= 3300;
            coords[1] = String.valueOf(Integer.parseInt(coords[1]) + 200);
        }
        coords[0] = String.valueOf(x);
    }
    private void decrementChildStack() {
        int updatedCount = childStack.pop() - 1;
        if (updatedCount != 0) {
            childStack.push(updatedCount);
        } else if (!coordStack.isEmpty()) {
            coords = coordStack.pop();
        }
    }

    public String translate() {
        List<NamedComponent> compsToProcess = new ArrayList<>(model.getRootComps());
        pushToStacks(compsToProcess.size());
        data.append(factory.assembleStart());
        while (!compsToProcess.isEmpty()) {
            NamedComponent current = compsToProcess.getFirst();
            if (!(current instanceof StmBody)) {
                data.append(transformComponent(current));
            }
            compsToProcess.removeFirst();
            updateStacksAfterTransform(1 + current.getContainedComponentsCount());

            Map<String, List<NamedComponent>> children = current.getChildren();
            if (children != null) {
                if (children.containsKey("components")) {
                    pushToStacks(children.get("components").size());
                    for (NamedComponent child : children.get("components")) {
                        if (compsToProcess.isEmpty()) {
                            compsToProcess.add(child);
                        } else {
                            compsToProcess.add(1, child);
                        }
                    }
                }
            }
        }
        data.append(factory.assembleEnd());
        return data.toString();
    }

    private String transformComponent(NamedComponent component) {
        String dataToReturn;
        switch (component) {
            case NameOnlyComponent nameOnlyComponent -> {
                switch (nameOnlyComponent.getType()) {
                    case RCPACKAGE, TYPE_DEC ->
                            dataToReturn = factory.assembleNameOnlyComp(nameOnlyComponent, coords);
                    default -> dataToReturn = factory.assembleContextComp((ContextComponent) component, coords);
                }
            }
            case RCModule rcModule -> dataToReturn = factory.assembleRCModule(rcModule, coords);
            case Junction junction -> dataToReturn = factory.assembleJunction(junction, coords);
            case ContextEventComponent contextEventComponent -> {
                switch (contextEventComponent.getType()) {
                    case REF -> dataToReturn = factory.assembleRef((Reference) component, coords);
                    case CONTROLLER, RP -> dataToReturn = factory.assembleConRpDef(contextEventComponent, coords);
                    case STATE -> dataToReturn = factory.assembleState((State) component, coords);
                    default -> dataToReturn = factory.assembleOpStmDef((StmComponent) component, coords);
                }
            }
            default -> throw new RuntimeException(
                    String.format("Error - unkown component '%s' supplied to TgtGenerator", component.getName()));
        }
        return dataToReturn;
    }
}
