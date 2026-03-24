package org.example.app.src;

import org.example.app.transformModel.*;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.SimpleCompType;
import org.example.app.transformModel.stm.JunctionType;

import java.util.*;

public class SrcParser {
    List<String> data;
    ComponentFactory factory;

    public SrcParser(ArrayList<String> data) {
        this.data = data;
    }

    public TransformModel parse() {
        factory = new ComponentFactory(data);
        int index = 0;
        while (index < data.size()) {
            index += patternMatch(index);
        }
        return factory.getDataModel();
    }

    private int patternMatch(int i) {
        int offset = 1;
        String pattern = data.get(i);
        switch (pattern) {
            case "package":
                offset = factory.createNameOnlyComp(data.get(i + 1), SimpleCompType.RCPACKAGE);
                break;
            case "type":
                offset = factory.createNameOnlyComp(data.get(i+1), SimpleCompType.TYPE_DEC);
                break;
            case "import":
                offset = factory.createImportComp(data.get(i+1));
                break;
            case "enumeration":
                offset = factory.createEnumRecord(i+1, SimpleCompType.ENUMERATION);
                break;
            case "record":
                offset = factory.createEnumRecord(i+1, SimpleCompType.RECORD);
                break;
            case "initial":
                offset = factory.createJunction(data.get(i+1), JunctionType.INITIAL);
                break;
            case "junction":
                offset = factory.createJunction(data.get(i+1), JunctionType.DEFAULT);
                break;
            case "probabilistic":
                offset = factory.createJunction(data.get(i+1), JunctionType.PROB);
                break;
            case "state":
                offset = factory.createState(data.get(i+1), false);
                break;
            case "final":
                offset = factory.createState(data.get(i+1), true);
                break;
            case "rref", "cref", "sref":
                offset = factory.createRef(data.get(i+1), data.get(i+3), false);
                break;
            case "opref":
                offset = factory.createRef(data.get(i+1), data.get(i+3), true);
                break;
            case "module", "stm", "interface", "controller":
                offset = factory.createModConIntRpStm(data.get(i+1), pattern);
                break;
            case "robotic":
                offset = factory.createModConIntRpStm(data.get(i+2), pattern);
                break;
            case "function":
                offset = factory.createFunction(i+1);
                break;
            case "operation":
                offset = factory.createOperation(i+1);
                break;
            case "connection":
                offset = factory.createConnection(i+1);
                break;
            case "transition":
                offset = factory.createTransition(i+1);
                break;
            case "event":
                offset = factory.createEvent(i+1);
                break;
            case "uses":
                offset = factory.createInterfaceRef(data.get(i+1), ContextType.D_INTERFACE);
                break;
            case "provides":
                offset = factory.createInterfaceRef(data.get(i+1), ContextType.P_INTERFACE);
                break;
            case "requires":
                offset = factory.createInterfaceRef(data.get(i+1), ContextType.R_INTERFACE);
                break;
            case "const":
                offset = factory.createVarConst(i+1, ContextType.CONSTANT);
                break;
            case "var":
                offset = factory.createVarConst(i+1, ContextType.VAR);
                break;
            case "clock":
                offset = factory.createClock(data.get(i+1));
                break;
            case "precondition":
                offset = factory.createPrePostCond(i+1, ContextType.PRECONDITION);
                break;
            case "postcondition":
                offset = factory.createPrePostCond(i+1, ContextType.POSTCONDITION);
                break;
            case "entry", "during", "exit":
                offset = factory.createStateAction(i+1, pattern);
                break;
            case "}":
                factory.attemptPopParentStack();
                break;
            default:
                // catch operation signature
                if (i != data.size()-1 && data.get(i+1).equals("(")) {
                    offset = factory.createOpSig(i);
                }
        }
        return offset;
    }
}
