package org.example.app.tgt;

import org.example.app.transformModel.Controller;
import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.State;
import org.example.persistence.XmlTemplates;

import java.util.ArrayList;
import java.util.List;

public class CellFactory {

    public String assembleStart() {
        return XmlTemplates.getStartBase();
    }
    public String assembleEnd() {
        return XmlTemplates.getEndBase();
    }

    // Package/TypeDec
    public String assembleNameOnlyComp(NameOnlyComponent component, String[] coords) {
        String[] template =
                (component.getType() == SimpleCompType.TYPE_DEC)? XmlTemplates.getTypeDec() : XmlTemplates.getPackage();
        String[] data = {
                String.valueOf(component.getId()),
                component.getName(),
                coords[0],
                coords[1]
        };
        return createCell(template, data);
    }

    // Imports/Enum/Record/Function/RCInterface
    public String assembleContextComp(ContextComponent component, String[] coords) {
        String[] data;
        if (component.getType() == SimpleCompType.IMPORTS) {
            data = new String[] {
                    String.valueOf(component.getId()),
                    coords[0],
                    coords[1]
            };
        } else {
            data = new String[] {
                    String.valueOf(component.getId()),
                    component.getName(),
                    coords[0],
                    coords[1]
            };
        }
        String[] template;
        switch (component.getType()) {
            case IMPORTS -> template = XmlTemplates.getImports();
            case ENUMERATION -> template = XmlTemplates.getEnum();
            case RECORD -> template = XmlTemplates.getRecord();
            case FUNCTION -> template = XmlTemplates.getFunction();
            default -> template = XmlTemplates.getInterface();
        }
        return createCell(template, data) + createContext(component.getContext(), String.valueOf(component.getId()));
    }

    // Module
    public String assembleRCModule(RCModule module, String[] coords) {
        String[] data = {
                module.getId() + "-0",
                module.getName(),
                coords[0],
                coords[1],
                String.valueOf(module.getId()),
                module.getId() + "-0"
        };
        return createCell(XmlTemplates.getModule(), data) +
                createConnections(module.getConnections());
    }

    // Junction
    public String assembleJunction(Junction junction, String[] coords) {
        String[] template = XmlTemplates.getJunction(junction.getType());
        String[] data = {
                String.valueOf(junction.getId()),
                String.valueOf(junction.getParentId()),
                coords[0],
                coords[1]
        };
        return createCell(template, data);
    }

    // Reference
    public String assembleRef(Reference ref, String[] coords) {
        String[] template;
        switch (ref.getReferencedObj().getType()) {
            case RP -> template = XmlTemplates.getRp(false);
            case OPERATION -> template = XmlTemplates.getOperation(false);
            case CONTROLLER -> template = XmlTemplates.getController(false);
            default -> template = XmlTemplates.getStm(false);
        }
        String[] data = {
                String.valueOf(ref.getId()),
                String.valueOf(ref.getParentId()),
                ref.getReferencedObj().getName(),
                coords[0],
                coords[1]
        };
        return createCell(template, data) +
                createEventBoxes(ref.getEventBoxes(), String.valueOf(ref.getId()), 0.7f);
    }

    // ControllerDef/RpDef
    public String assembleConRpDef(ContextEventComponent component, String[] coords) {
        String connections = "";
        String[] template;
        float multiplier;
        if (component.getType() == ComplexCompType.CONTROLLER) {
            connections = createConnections(((Controller) component).getConnections());
            template = XmlTemplates.getController(true);
            multiplier = component.getContainedComponentsCount();
        } else {
            template = XmlTemplates.getRp(true);
            multiplier = 0.94f;
        }
        String[] data = {
                component.getId() + "-0",
                String.valueOf(component.getParentId()),
                component.getName(),
                coords[0],
                coords[1],
                createContext(component.getContext(), (component.getId()+"-0")),
                String.valueOf(component.getId()),
                component.getId() + "-0"
        };
        return createCell(template, data) +
                createEventBoxes(
                        component.getEventBoxes(),
                        String.valueOf(component.getId()),
                        multiplier) +
                connections;
    }

    // State (incl. final)
    public String assembleState(State state, String[] coords) {
        String[] template;
        String[] data;
        String dataToReturn;
        if (state.isFinalState()) {
            template = XmlTemplates.getState(true);
            data = new String[]{
                    String.valueOf(state.getId()),
                    String.valueOf(state.getParentId()),
                    coords[0],
                    coords[1]
            };
            dataToReturn = createCell(template, data);
        } else {
            template = XmlTemplates.getState(false);
            data = new String[]{
                    String.valueOf(state.getId()),
                    String.valueOf(state.getParentId()),
                    state.getName(),
                    coords[0],
                    coords[1],
                    createContext(state.getContext(), String.valueOf(state.getId())),
                    String.valueOf(state.getBody().getId()),
                    String.valueOf(state.getId())
            };
            dataToReturn = createCell(template, data) +
                    createConnections(state.getBody().getTransitions());
        }
        return dataToReturn;
    }

    // OpDef/StmDef
    public String assembleOpStmDef(StmComponent component, String[] coords) {
        String[] template =
                (component.getType() == ComplexCompType.STM)?
                        XmlTemplates.getStm(true) : XmlTemplates.getOperation(true);
        String[] data = {
                String.valueOf(component.getId()),
                String.valueOf(component.getParentId()),
                component.getName(),
                coords[0],
                coords[1],
                createContext(component.getContext(), String.valueOf(component.getId())),
                String.valueOf(component.getBody().getId()),
                String.valueOf(component.getId())
        };
        return createCell(template, data) +
                createEventBoxes(
                        component.getEventBoxes(),
                        String.valueOf(component.getBody().getId()),
                        component.getContainedComponentsCount()) +
                createConnections(component.getBody().getTransitions());
    }

    // EventBoxes
    public String createEventBoxes(List<EventBox> boxes, String parentID, float multiplier) {
        String estimatedXVal = String.valueOf(Math.round(multiplier * 160));
        String y = "0";
        String[] template = XmlTemplates.getEventBox();
        StringBuilder dataToReturn = new StringBuilder();
        for (EventBox box : boxes) {
            String[] data = {
                    String.valueOf(box.getId()),
                    parentID,
                    box.getName(),
                    estimatedXVal,
                    y
            };
            dataToReturn.append(createCell(template, data));
            y = String.valueOf(Integer.parseInt(y) + 25);
        }
        return dataToReturn.toString();
    }

    // Connections/Transitions
    public String createConnections(List<Connection> conns) {
        StringBuilder dataToReturn = new StringBuilder();
        for (Connection connection : conns) {
            List<String> data = new ArrayList<>(List.of(
                    String.valueOf(connection.getId()),
                    String.valueOf(connection.getParentId()),
                    String.valueOf(connection.getSrc()),
                    String.valueOf(connection.getTgt()),
                    connection.getId() + "-0",
                    String.valueOf(connection.getId()),
                    connection.getLabel()
            ));
            String[] template = XmlTemplates.getConnection(connection.isBidi());
            if (!connection.isBidi()) {
                String fontstyle = "0";
                if (connection.isAsync()) {
                    fontstyle = "2";
                }
                data.add(6, fontstyle);
            }
            dataToReturn.append(createCell(template, data.toArray(new String[0])));
        }
        return dataToReturn.toString();
    }

    // ContextData
    public String createContext(List<ContextData> contextList, String parentID) {
        StringBuilder contextToReturn = new StringBuilder();
        for (ContextData contextLine : contextList) {
            String[] template = getContextTemplate(contextLine.getType());
            String[] data = {
                    String.valueOf(contextLine.getId()),
                    parentID,
                    contextLine.getName()
            };
            contextToReturn.append(createCell(template, data));
        }
        return contextToReturn.toString();
    }

    private String[] getContextTemplate(ContextType type) {
        String[] template;
        switch (type) {
            case TEXT -> template = XmlTemplates.getTextRow();
            case VAR -> template = XmlTemplates.getVar();
            case CLOCK -> template = XmlTemplates.getClock();
            case EVENT -> template = XmlTemplates.getEvent();
            case OP_SIG -> template = XmlTemplates.getOpSig();
            case CONSTANT -> template = XmlTemplates.getConstant();
            case D_INTERFACE -> template = XmlTemplates.getInterfaceRef(type);
            case P_INTERFACE -> template = XmlTemplates.getInterfaceRef(type);
            case R_INTERFACE -> template = XmlTemplates.getInterfaceRef(type);
            case PRECONDITION -> template = XmlTemplates.getPrecondition();
            default -> template = XmlTemplates.getPostcondition();
        }
        return template;
    }

    public String createCell(String[] template, String[] data) {
        StringBuilder mxCell = new StringBuilder(template[0]);
        for (int i=0; i<data.length; i++) {
            mxCell.append(data[i]);
            mxCell.append(template[i+1]);
        }
        return mxCell.toString();
    }
}
