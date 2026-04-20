package org.example.app.transformModel.connection;

import org.example.app.transformModel.generalComps.NamedComponent;

/**
 * Class that represents RoboChart Connections and Transitions.
 */
public class Connection  extends NamedComponent {
    /**
     * integer representing the ID of the source of the component.
     */
    private int src;
    /**
     * integer representing the ID of the target of the component.
     */
    private int tgt;
    /**
     * boolean indicating whether the connection is bidirectional or not (always false for transitions).
     */
    private boolean bidi;
    /**
     * String representing the label to be displayed in the target diagram alongside the connection/transition.
     */
    private String label;

    // For connections between event boxes
    public Connection(int id, int parentId, int src, int tgt) {
        String name = src+"-to-"+tgt;
        super(id, name, parentId);
        this.src = src;
        this.tgt = tgt;
        label = "";
    }

    // For transitions in state diagrams
    public Connection(int id, String name, int parentId, int src, int tgt, String label) {
        super(id, name, parentId);
        this.label = label.replace("<", "%lt;").replace(">", "%gt;");
        this.src = src;
        this.tgt = tgt;
        this.bidi = false;
    }

    public Connection(int id, String label, int parentId, int src, int tgt, boolean bidi) {
        String name = src+"-to-"+tgt;
        super(id, name, parentId);
        this.src = src;
        this.tgt = tgt;
        this.label = label;
        this.bidi = bidi;
    }

    public int getSrc() {
        return src;
    }
    public int getTgt() {
        return tgt;
    }

    public boolean isBidi() {
        return bidi;
    }
    public void setBidi(boolean bidi) {
        this.bidi = bidi;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Method checks if the connection is asynchronous.
     * @return true if the connection is asynchronous, otherwise false.
     */
    public boolean isAsync() {
        return label.equals("async");
    }
    /**
     * Method makes the connection is asynchronous by setting {@code label} to 'async'.
     */
    public void makeAsync() {
        label = "async";
    }

    @Override
    public void addChild(NamedComponent child) {
        throw new RuntimeException(
                String.format(
                        "Cannot make component %s a child of connection %s",
                        child.getName(),
                        name));
    }
}
