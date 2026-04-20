package org.example.app.transformModel;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents one of the following RoboChart components: State Machine reference, Operation reference,
 * Controller reference, Robotic Platform reference.
 */
public class Reference extends ContextEventComponent {
    /**
     * ContextEventComponent instance representing the component definition which this component references.
     */
    private ContextEventComponent referencedObj;

    public Reference(int id, String name, int parentId, ContextEventComponent referencedObj) {
        super(id, name, parentId, ComplexCompType.REF);
        this.referencedObj = referencedObj;
        context = null;
    }

    public Reference(int id, String name, ContextEventComponent referencedObj) {
        super(id, name, ComplexCompType.REF);
        this.referencedObj = referencedObj;
        context = null;
    }

    /**
     * Uses the {@code referencedObj} to generate event boxes from the component this component references.
     * @return List of EventBox instances representing this component's event boxes.
     */
    public List<EventBox> createEventBoxes() {
        List<EventBox> refEventBoxes = referencedObj.getEventBoxes();
        int currentId = id + 1;
        for (EventBox refBox : refEventBoxes) {
            EventBox newBox = new EventBox(currentId, refBox.getName(), id);
            this.addEventBox(newBox);
            currentId++;
        }
        return this.getEventBoxes();
    }

    public ContextEventComponent getReferencedObj() {
        return referencedObj;
    }

    @Override
    public List<ContextData> getContext() {
        throw new RuntimeException("Cannot return context for a reference");
    }

    @Override
    public void addContextLine(ContextData contextLine) {
        throw new RuntimeException("Cannot add context to a reference");
    }

    @Override
    public void addChild(NamedComponent child) {
        if (child instanceof EventBox){
            super.addChild(child);
        } else {
            throw new RuntimeException(
                    String.format(
                            "Cannot make component %s a child of reference %s",
                            child.getName(),
                            name
                    )
            );
        }
    }

    @Override
    public Map<String, List<NamedComponent>> getChildren() {
        Map<String, List<NamedComponent>> children = new HashMap<>();
        List<NamedComponent> eventBoxList = new ArrayList<>(eventBoxes);
        children.put("eventBox", eventBoxList);
        return children;
    }

}
