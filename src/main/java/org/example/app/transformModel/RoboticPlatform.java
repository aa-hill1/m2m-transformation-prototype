package org.example.app.transformModel;

import org.example.app.transformModel.generalComps.ContextEventComponent;

public class RoboticPlatform extends ContextEventComponent {
    public RoboticPlatform(int id, String name) {
        super(id, name);
    }

    public RoboticPlatform(int id, String name, int parentId) {
        super(id, name, parentId);
    }
}
