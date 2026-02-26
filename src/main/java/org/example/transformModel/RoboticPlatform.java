package org.example.transformModel;

import org.example.transformModel.generalComps.ContextEventComponent;

public class RoboticPlatform extends ContextEventComponent {
    public RoboticPlatform(String name) {
        super(name);
    }

    public RoboticPlatform(String name, int parentId) {
        super(name, parentId);
    }
}
