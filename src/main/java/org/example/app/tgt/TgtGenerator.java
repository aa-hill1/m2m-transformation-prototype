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

/**
 * Class that creates an instance of the target model, given its transformation model equivalent in the form of an
 * instance of TransformModel.
 */
public class TgtGenerator {
    /**
     * instance of the transformation model to translate to the target.
     */
    private TransformModel model;
    /**
     * CellFactory instance used to generate target model components.
     */
    private CellFactory factory;
    /**
     * StringBuilder instance used to hold the target model during its generation.
     */
    private StringBuilder data;
    /**
     * Stack used to help handle component spacing by tracking x and y coordinates for components at different levels
     * of containment.
     */
    private Stack<String[]> coordStack;
    /**
     * Stack used to help handle component spacing by tracking the number of components to process and their
     * containment level.
     */
    private Stack<Integer> childStack;
    /**
     * Array of strings containing the x and y coordinates of the current component being generated.
     */
    private String[] coords;

    /**
     * Constructor for TgtGenerator class.
     * @param model
     * TransformModel instance containing model instance to transform.
     */
    public TgtGenerator(TransformModel model) {
        this.model = model;
        factory = new CellFactory();
        data = new StringBuilder();
        coordStack = new Stack<>();
        childStack = new Stack<>();
        coords = new String[]{"0", "0"};
    }

    // Used in testing with CellFactory mock.
    public TgtGenerator(CellFactory factory) {
        this.factory = factory;
        coords = new String[]{"0", "0"};
    }

    /**
     * Method pushes the number of children in a component to the {@code childStack}, then pushes the current value of
     * {@code coords} to the {@code coordStack} to allow for help with spacing for a component's children.
     * @param childCount
     * integer representing the number of children of a given component.
     */
    private void pushToStacks(int childCount) {
        if (!childStack.isEmpty()) {
            coordStack.push(coords);
            coords = new String[]{"0", "0"};
        }
        childStack.push(childCount);
    }

    /**
     * Method handles updating the {@code coordStack} and {@code childStack} after a component has been transformed to
     * help keep track of component spacing in the target model.
     * @param offset
     * integer representing the offset to add to {@code coords} so that components are appropriately spaced.
     */
    private void updateStacksAfterTransform(int offset) {
        updateCoords(offset);
        decrementChildStack();
    }

    /**
     * Method updates the current value of {@code coords} to help with component spacing.
     * @param offset
     * integer representing the x coordinate offset to use to position the next component to transform.
     */
    private void updateCoords(int offset) {
        int x = Integer.parseInt(coords[0]);
        x += offset * 160;
        if (x > 3300) {
            x %= 3300;
            coords[1] = String.valueOf(Integer.parseInt(coords[1]) + 200);
        }
        coords[0] = String.valueOf(x);
    }

    /**
     * Decrements the integer at the top of {@code childStack} after a component has been transformed, popping from the
     * stack if the top value becomes zero and returning the stored value of {@code coords} from {@code coordStack}.
     */
    private void decrementChildStack() {
        int updatedCount = childStack.pop() - 1;
        if (updatedCount != 0) {
            childStack.push(updatedCount);
        } else if (!coordStack.isEmpty()) {
            coords = coordStack.pop();
        }
    }

    /**
     * Method traverses the TransformModel instance {@code model}, transforming each component to its target model
     * equivalent and returning the final complete model.
     * @return string representing the target model instance.
     */
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

    /**
     * Method transforms and returns a given transformation model component to its target model equivalent.
     * @param component
     * NamedComponent instance representing the component to transform.
     * @return string representing the target model equivalent of {@code component}.
     */
    public String transformComponent(NamedComponent component) {
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
