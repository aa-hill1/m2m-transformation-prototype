package org.example.app;

import org.example.app.transformModel.*;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.ContextComponent;
import org.example.app.transformModel.generalComps.NameOnlyComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.SimpleCompType;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.example.app.transformModel.stm.State;
import org.example.app.transformModel.stm.StateMachine;

import java.util.*;

public class SrcParser {
    int nextId = 2;
    Stack<Integer> parentStack = new Stack<>();
    int importsId = -1;
    List<String> data;
    TransformModel model;

    public SrcParser(ArrayList<String> data) {
        this.data = data;
        parentStack.push(1);
    }

    public TransformModel parse() {
        model = new TransformModel();
        int index = 0;
        while (index < data.size()) {
            index += patternMatch(index);
        }
        return model;
    }

    //TODO update model instance on each
    private int createNameOnlyComp(String name, SimpleCompType type) {
        NameOnlyComponent component = new NameOnlyComponent(this.useNextId(), name, type);
        model.addNewComp(component);
        return 2;
    }
    private int createImportComp(String name) {
        ContextComponent imports;
        if (importsId != -1) {
            imports = (ContextComponent) model.getCompById(importsId);
        } else {
            imports = new ContextComponent(this.useNextId(), "imports", SimpleCompType.IMPORTS);
            importsId = imports.getId();
            model.addNewComp(imports);
        }
        imports.addContextLine(new ContextData(this.useNextId(), name, importsId, ContextType.TEXT));
        return 2;
    }
    //startIndex - index following that of pattern
    private int createEnumRecord(int startIndex, SimpleCompType type) {
        ContextComponent component = new ContextComponent(this.useNextId(), data.get(startIndex), type);
        int contextSize = (type==SimpleCompType.RECORD)? 3 : 1;
        int endIndex = this.findCompEnd(startIndex);
        for (int i=startIndex+2; i<endIndex; i+=contextSize) {
            String contextRowName = (type==SimpleCompType.RECORD)? buildString(i, i+3, true) : data.get(i);
            component.addContextLine(new ContextData(this.useNextId(), contextRowName, component.getId(), ContextType.TEXT));
        }
        return (2 + (startIndex - endIndex));
    }
    private int createJunction(String name, JunctionType type) {
        Junction junction = new Junction(this.useNextId(), name, parentStack.peek(), type);
        return 2;
    } //TODO add to parent
    private int createRef(String name, String defName) {
        Reference ref = new Reference(this.useNextId(), name, parentStack.peek(), model.getCompByName(defName));
        this.incrementNextId(ref.createEventBoxes());
        return 4;
    } //TODO add to parent
    private int createModConIntRpStm(String name, String pattern) {
        NamedComponent component;
        int offsetToReturn = 3;
        switch (pattern) {
            case "module":
                component = new RCModule(this.useNextId(), name);
                break;
            case "controller":
                component = new Controller(this.useNextId(), name, parentStack.peek());
                break;
            case "interface":
                component = new ContextComponent(this.useNextId(), name, parentStack.peek(), SimpleCompType.RCINTERFACE);
                break;
            case "stm":
                component = new StateMachine(this.useNextId(), name, parentStack.peek());
                break;
            default:
                component = new RoboticPlatform(this.useNextId(), name, parentStack.peek());
                offsetToReturn = 4;
        }
        parentStack.push(component.getId());
        return offsetToReturn;
    } //TODO add to parent
    private int createFunction(int start) {
        int nameEnd = findStringIndex(start, "{");
        String name = addSpacesToStr(buildString(start, nameEnd, false), List.of(',', ':'));
        ContextComponent component = new ContextComponent(this.useNextId(), name, SimpleCompType.FUNCTION);
        parentStack.push(component.getId());
        return 1 + (nameEnd-start);
    }
    private int createState(String name, boolean isFinal) {
        int offset = 2;
        State state = new State(this.useNextId(), name, parentStack.peek(), isFinal);
        if (!isFinal) {
            parentStack.push(state.getId());
            offset = 3;
        }
        return offset;
    } //TODO add to parent
    private int createOperation(int start) {
        int nameEnd = findStringIndex(start, "{");
        String name = addSpacesToStr(buildString(start, nameEnd, false), List.of(',', ':'));
        if (data.subList(nameEnd, findStringIndex(nameEnd+1, "}")).contains("terminates")) {
            name += " [terminates]";
        }
        Operation operation = new Operation(this.useNextId(), name, parentStack.peek());
        this.incrementNextId(1); //For StmBody within operation
        parentStack.push(operation.getId());
        return 1 + (nameEnd-start);
    }

    private String addSpacesToStr(String text, List<Character> triggers) {
        StringBuilder textToChange = new StringBuilder(text);
        for (int i=0; i<textToChange.length(); i++) {
            if (triggers.contains(textToChange.charAt(i))) {
                textToChange.insert(i+1, " ");
            }
        }
        return textToChange.toString();
    }
    private String buildString(int start, String cutOff, boolean spaceSeparated) {
        return formString(start, findStringIndex(start, cutOff), spaceSeparated);
    }
    private String buildString(int start, int end, boolean spaceSeparated) {
        return formString(start, end, spaceSeparated);
    }
    private int findStringIndex(int start, String toFind) {
        int found = -1;
        if (toFind.equals("}")) {
            found = findCompEnd(start);
        } else {
            for (int i=start; i<data.size(); i++) {
                if (data.get(i).equals(toFind)) {
                    found = i;
                    break;
                }
            }
        }
        return found;
    }
    private int findStringIndex(int start, List<String> toFind) {
        int found = -1;
        for (int i=start; i<data.size(); i++) {
            if (toFind.contains(data.get(i))) {
                found = i;
                break;
            }
        }
        return found;
    }
    private int findCompEnd(int start) {
        Stack<Character> compStack = new Stack<>();
        compStack.add('{');
        int current = start;
        while (!compStack.isEmpty()) {
            current++;
            if (data.get(current).equals("}")) {
                compStack.pop();
            } else if (data.get(current).equals("{")) {
                compStack.push('{');
            }
        }
        return current;
    }
    private String formString(int start, int end, boolean spaceSeparated) {
        int currentIndex = start;
        String current = data.get(currentIndex);
        StringBuilder strToReturn = new StringBuilder();
        while (currentIndex < end) {
            if (spaceSeparated && (currentIndex != start)) {
                strToReturn.append(" ");
            }
            strToReturn.append(current);
            currentIndex++;
            current = data.get(currentIndex);
        }
        return strToReturn.toString();
    }

    private int patternMatch(int i) {
        int offset;
        String pattern = data.get(i);
        switch (pattern) {
            case "package":
                offset = createNameOnlyComp(data.get(i+1), SimpleCompType.RCPACKAGE);
                break;
            case "type":
                offset = createNameOnlyComp(data.get(i+1), SimpleCompType.TYPE_DEC);
                break;
            case "import":
                offset = createImportComp(data.get(i+1));
                break;
            case "enumeration":
                offset = createEnumRecord(i+1, SimpleCompType.ENUMERATION);
                break;
            case "record":
                offset = createEnumRecord(i+1, SimpleCompType.RECORD);
                break;
            case "initial":
                offset = createJunction(data.get(i+1), JunctionType.INITIAL);
                break;
            case "junction":
                offset = createJunction(data.get(i+1), JunctionType.DEFAULT);
                break;
            case "probabilistic":
                offset = createJunction(data.get(i+1), JunctionType.PROB);
                break;
            case "state":
                offset = createState(data.get(i+1), false);
                break;
            case "final":
                offset = createState(data.get(i+1), true);
                break;
            case "rref", "cref", "sref":
                offset = createRef(data.get(i+1), data.get(i+3));
                break;
            case "module", "stm", "interface", "controller":
                offset = createModConIntRpStm(data.get(i+1), pattern);
                break;
            case "robotic":
                offset = createModConIntRpStm(data.get(i+2), pattern);
                break;
            case "function":
                offset = createFunction(i+1);
                break;
            case "operation":
                offset = createOperation(i+1);
                break;
            case "connection":
                break;
            case "transition":
                break;
            case "event":
                break;
            case "uses": //TODO - remember EventBoxes
                break;
            case "provides":
                break;
            case "requires":
                break;
            case "const":
                break;
            case "var":
                break;
            case "clock":
                break;
            case "precondition":
                break;
            case "postcondition":
                break;
            case "entry":
                break;
            case "during":
                break;
            case "exit":
                break;
            default:
                if (pattern.contains("(") && pattern.contains(")"));
        }
        return offset;
    }

    private String createNameTypePair(int start) {
        List<String> pairData = data.subList(start, start+3);
        return String.format("%s : %s", pairData.get(0), pairData.get(2));
    }

    private int useNextId() {
        nextId++;
        return (nextId-1);
    }
    private void incrementNextId(int compsMade) {
        nextId += compsMade;
    }

}
