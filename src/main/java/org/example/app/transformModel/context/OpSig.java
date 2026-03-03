package org.example.app.transformModel.context;

import org.example.app.transformModel.Operation;

public class OpSig extends ContextData {
    private Operation operation;

    public OpSig(int id, String name, int parentId, Operation operation) {
        super(id, name, parentId, ContextType.OP_SIG);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
