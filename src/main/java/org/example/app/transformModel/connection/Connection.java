package org.example.app.transformModel.connection;

import org.example.app.transformModel.generalComps.NamedComponent;

public class Connection  extends NamedComponent {
    private int src;
    private int tgt;
    private boolean bidi;
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
        this.label = label;
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

    public boolean isAsync() {
        return label.equals("async");
    }
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
