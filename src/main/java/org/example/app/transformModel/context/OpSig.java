package org.example.transformModel.context;

import org.example.transformModel.Operation;

public class OpSig extends ContextData {
    private Operation operation;

    public OpSig(String name, int parentId, Operation operation) {
        super(name, parentId, ContextType.OP_SIG);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
