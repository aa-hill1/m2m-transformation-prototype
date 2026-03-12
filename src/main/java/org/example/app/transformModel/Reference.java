package org.example.app.transformModel;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.List;

public class Reference extends ContextEventComponent {
    private NamedComponent referencedObj;

    public Reference(int id, String name, int parentId, NamedComponent referencedObj) {
        super(id, name, parentId);
        this.referencedObj = referencedObj;
        context = null;
    }

    public Reference(int id, String name, NamedComponent referencedObj) {
        super(id, name);
        this.referencedObj = referencedObj;
        context = null;
    }

    public List<EventBox> createEventBoxes() {
        if (!(referencedObj instanceof Operation)) {
            List<EventBox> refEventBoxes = ((ContextEventComponent) referencedObj).getEventBoxes();
            int currentId = id + 1;
            for (EventBox refBox : refEventBoxes) {
                EventBox newBox = new EventBox(currentId, (name + refBox.getName()), id, refBox.getEvent());
                this.addEventBox(newBox);
                currentId++;
            }
        }
        return this.getEventBoxes();
    }

    public NamedComponent getReferencedObj() {
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
}
