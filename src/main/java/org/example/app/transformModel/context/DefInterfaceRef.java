package org.example.transformModel.context;

import org.example.transformModel.generalComps.ContextComponent;

public class DefInterfaceRef extends ContextData {
    private ContextComponent rcInterface;

    public DefInterfaceRef(String name, int parentId, ContextComponent rcInterface) {
        super(name, parentId, ContextType.D_INTERFACE);
        this.rcInterface = rcInterface;
    }

    public ContextComponent getRcInterface() {
        return rcInterface;
    }
}
