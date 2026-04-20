package org.example.app.transformModel.context;

import org.example.app.transformModel.generalComps.NamedComponent;

/**
 * Class that represents all types of context data in Robochart. This includes: Events, Provided interfaces, Required
 * interfaces, Defined interfaces, Preconditions, Postconditions, Clocks, Actions, Operation Signatures, Variables,
 * and Constants.
 */
public class ContextData extends NamedComponent {
    /**
     * ContextType to distinguish between different types of context data.
     */
    protected ContextType type;

    public ContextData(int id, String name, int parentId, ContextType type) {
        super(id, name.replace("<", "%lt;").replace(">", "%gt;"), parentId);
        this.type = type;
    }

    public ContextType getType() {
        return type;
    }

    @Override
    public void addChild(NamedComponent child) {
        throw new RuntimeException(
                String.format(
                        "Cannot make component %s a child of contextData %s",
                        child.getName(),
                        name));
    }
}
