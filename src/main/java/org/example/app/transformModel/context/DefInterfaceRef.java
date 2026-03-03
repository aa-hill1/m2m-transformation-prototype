package org.example.app.transformModel.context;

import org.example.app.transformModel.generalComps.ContextComponent;

public class DefInterfaceRef extends ContextData {
    private ContextComponent rcInterface;

    public DefInterfaceRef(int id, String name, int parentId, ContextComponent rcInterface) {
        super(id, name, parentId, ContextType.D_INTERFACE);
        this.rcInterface = rcInterface;
    }

    public ContextComponent getRcInterface() {
        return rcInterface;
    }
}
