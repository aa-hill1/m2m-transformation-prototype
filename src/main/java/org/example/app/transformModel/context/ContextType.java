package org.example.app.transformModel.context;

/**
 * Enum representing every type of RoboChart Context data.
 */
public enum ContextType {
    TEXT, // For Actions, Enumeration types and Record data
    PRECONDITION,
    POSTCONDITION,
    EVENT,
    OP_SIG,
    CONSTANT,
    VAR,
    CLOCK,
    R_INTERFACE, // Required Interface
    P_INTERFACE, // Provided Interface
    D_INTERFACE // Defined Interface
}
