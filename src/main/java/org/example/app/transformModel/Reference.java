package org.example.transformModel;

import org.example.transformModel.generalComps.NamedComponent;

import java.util.ArrayList;
import java.util.List;

public class Reference extends NamedComponent {
    private NamedComponent referencedObj;
    private List<EventBox> eventBoxes = new ArrayList<>();

    public Reference(String name, int parentId, NamedComponent referencedObj) {
        super(name, parentId);
        this.referencedObj = referencedObj;
    }

    public Reference(String name, NamedComponent referencedObj) {
        super(name);
        this.referencedObj = referencedObj;
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
