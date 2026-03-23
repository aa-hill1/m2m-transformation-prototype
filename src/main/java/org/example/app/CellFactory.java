package org.example.app;

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

    public String assembleNameOnlyComp(NameOnlyComponent component, int[] coords, boolean typeDec) {
        String[] template = (typeDec)? XmlTemplates.TYPE_DEC : XmlTemplates.PACKAGE;
        String[] data = {
                String.valueOf(component.getId()),
                component.getName(),
                String.valueOf(coords[0]),
                String.valueOf(coords[1])
        };
        return createCell(template, data);
    }

    public String assembleContextComp(ContextComponent component, int[] coords, boolean imports) {
        String[] data;
        if (imports) {
            data = new String[] {
                    String.valueOf(component.getId()),
                    String.valueOf(coords[0]),
                    String.valueOf(coords[1])
            };
        } else {
            data = new String[] {
                    String.valueOf(component.getId()),
                    component.getName(),
                    String.valueOf(coords[0]),
                    String.valueOf(coords[1])
            };
        }
        String[] template;
        switch (component.getType()) {
            case IMPORTS -> template = XmlTemplates.IMPORTS;
            case ENUMERATION -> template = XmlTemplates.ENUM;
            case RECORD -> template = XmlTemplates.RECORD;
            case FUNCTION -> template = XmlTemplates.FUNCTION;
            default -> template = XmlTemplates.INTERFACE;
        }
        return createCell(template, data) + createContext(component.getContext(), String.valueOf(component.getId()));
    }

    public String assembleRCModule(RCModule module, int[] coords) {
        String[] data = {
                module.getId() + "-0",
                module.getName(),
                String.valueOf(coords[0]),
                String.valueOf(coords[1]),
                String.valueOf(module.getId()),
                module.getId() + "-0"
        };
        return createCell(XmlTemplates.MODULE, data) +
                createConnections(module.getConnections());
    }

    public String assembleJunction(Junction junction, int[] coords) {
        String[] template;
        switch (junction.getType()) {
            case DEFAULT -> template = XmlTemplates.JUNCTION;
            case INITIAL -> template = XmlTemplates.INITIAL_JUNCTION;
            default -> template = XmlTemplates.PROB_JUNCTION;
        }
        String[] data = {
                String.valueOf(junction.getId()),
                String.valueOf(junction.getParentId()),
                String.valueOf(coords[0]),
                String.valueOf(coords[1])
        };
        return createCell(template, data);
    }

    public String assembleRef(Reference ref, int[] coords) {
        String[] template;
        switch (ref.getReferencedObj().getType()) {
            case RP -> template = XmlTemplates.RP_REF;
            case OPERATION -> template = XmlTemplates.OP_REF;
            case CONTROLLER -> template = XmlTemplates.CONTROLLER_REF;
            default -> template = XmlTemplates.STM_REF;
        }
        String[] data = {
                String.valueOf(ref.getId()),
                String.valueOf(ref.getParentId()),
                ref.getReferencedObj().getName(),
                String.valueOf(coords[0]),
                String.valueOf(coords[1])
        };
        return createCell(template, data) +
                createEventBoxes(ref.getEventBoxes(), String.valueOf(ref.getId()), 0.4f);
    }

    public String assembleConRpDef(ContextEventComponent component, int[] coords) {
        String connections = "";
        String[] template;
        float multiplier;
        if (component.getType() == ComplexCompType.CONTROLLER) {
            connections = createConnections(((Controller) component).getConnections());
            template = XmlTemplates.CONTROLLER_DEF;
            multiplier = component.getContainedComponentsCount();
        } else {
            template = XmlTemplates.RP_DEF;
            multiplier = 0.54f;
        }
        String[] data = {
                component.getId() + "-0",
                String.valueOf(component.getParentId()),
                component.getName(),
                String.valueOf(coords[0]),
                String.valueOf(coords[1]),
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

    public String assembleState(State state, int[] coords) {
        String[] template;
        String[] data;
        String dataToReturn;
        if (state.isFinalState()) {
            template = XmlTemplates.FINAL_STATE;
            data = new String[]{
                    String.valueOf(state.getId()),
                    String.valueOf(state.getParentId()),
                    String.valueOf(coords[0]),
                    String.valueOf(coords[1])
            };
            dataToReturn = createCell(template, data);
        } else {
            template = XmlTemplates.STATE;
            data = new String[]{
                    String.valueOf(state.getId()),
                    String.valueOf(state.getParentId()),
                    state.getName(),
                    String.valueOf(coords[0]),
                    String.valueOf(coords[1]),
                    createContext(state.getContext(), String.valueOf(state.getId())),
                    String.valueOf(state.getBody().getId()),
                    String.valueOf(state.getId())
            };
            dataToReturn = createCell(template, data) +
                    createConnections(state.getBody().getTransitions());
        }
        return dataToReturn;
    }

    public String assembleOpStmDef(StmComponent component, int[] coords) {
        String[] template =
                (component.getType() == ComplexCompType.STM)? XmlTemplates.STM_DEF : XmlTemplates.OP_DEF;
        String[] data = {
                String.valueOf(component.getId()),
                String.valueOf(component.getParentId()),
                component.getName(),
                String.valueOf(coords[0]),
                String.valueOf(coords[1]),
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

    public String createEventBoxes(List<EventBox> boxes, String parentID, float multiplier) {
        String estimatedXVal = String.valueOf(Math.round(multiplier * 280));
        String y = "0";
        String[] template = XmlTemplates.EVENT_BOX;
        StringBuilder dataToReturn = new StringBuilder();
        for (EventBox box : boxes) {
            String[] data = {
                    String.valueOf(box.getId()),
                    parentID,
                    estimatedXVal,
                    y
            };
            dataToReturn.append(createCell(template, data));
            y = String.valueOf(Integer.parseInt(y) + 25);
        }
        return dataToReturn.toString();
    }

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
            String[] template;
            if (!connection.isBidi()) {
                template = XmlTemplates.CONNECTION;
                String fontstyle = "0";
                if (connection.isAsync()) {
                    fontstyle = "2";
                }
                data.add(5, fontstyle);
            } else {
                template = XmlTemplates.BIDI_CONNECTION;
            }
            dataToReturn.append(createCell(template, data.toArray(new String[0])));
        }
        return dataToReturn.toString();
    }

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
            case TEXT -> template = XmlTemplates.TEXT_ROW;
            case VAR -> template = XmlTemplates.VAR;
            case CLOCK -> template = XmlTemplates.CLOCK;
            case EVENT -> template = XmlTemplates.EVENT;
            case OP_SIG -> template = XmlTemplates.OP_SIG;
            case CONSTANT -> template = XmlTemplates.CONSTANT;
            case D_INTERFACE -> template = XmlTemplates.DEF_INTERFACE;
            case P_INTERFACE -> template = XmlTemplates.PROV_INTERFACE;
            case R_INTERFACE -> template = XmlTemplates.REQ_INTERFACE;
            case PRECONDITION -> template = XmlTemplates.PRECONDITION;
            default -> template = XmlTemplates.POSTCONDITION;
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
