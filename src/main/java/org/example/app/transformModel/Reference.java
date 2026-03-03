package org.example.app.transformModel;

import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.List;

public class Reference extends NamedComponent {
    private NamedComponent referencedObj;
    private List<EventBox> eventBoxes = new ArrayList<>();

    public Reference(int id, String name, int parentId, NamedComponent referencedObj) {
        super(id, name, parentId);
        this.referencedObj = referencedObj;
    }

    public Reference(int id, String name, NamedComponent referencedObj) {
        super(id, name);
        this.referencedObj = referencedObj;
    }

    public int createEventBoxes() {
        int boxesCreated = 0;
        if (!(referencedObj instanceof Operation)) {
            List<EventBox> refEventBoxes = ((ContextEventComponent) referencedObj).getEventBoxes();
            int currentId = id + 1;
            for (EventBox refBox : refEventBoxes) {
                EventBox newBox = new EventBox(currentId, (name + refBox.getName()), id, refBox.getEvent());
                currentId++;
            }
            boxesCreated = refEventBoxes.size();
        }
        return boxesCreated;
    }

    public NamedComponent getReferencedObj() {
        return referencedObj;
    }

    public List<EventBox> getEventBoxes() {
        return eventBoxes;
    }

    public void addEventBox(EventBox eventBox) {
        if (eventBoxes.contains(eventBox)) {
            throw new RuntimeException(String.format("Cannot add event box %s to reference %s twice", eventBox.getName(), name));
        }
        eventBoxes.add(eventBox);
    }
}
