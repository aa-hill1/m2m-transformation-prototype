package org.example.app.transformModel.generalComps;

/**
 * Enum representing the possible types of {@code ContextEventComponent} and {@code StmComponent} instances.
 */
public enum ComplexCompType {
    CONTROLLER, // Controller class only
    OPERATION, // StmComponent class only
    STATE, // State class only
    STM, // StmComponent class only
    REF, // Reference class only
    RP // ContextEventComponent class only
}
