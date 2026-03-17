package org.example.app;

import org.example.app.transformModel.*;
import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.context.DefInterfaceRef;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.*;

import java.util.List;
import java.util.Stack;

/**
 * Class that handles the construction of a TransformModel instance.
 */
public class ComponentFactory {
    /**
     * Stack tracking the current parent ID, default value is 1.
     */
    private Stack<Integer> parentStack = new Stack<>();

    /**
     * List containing .rct data, split by spaces and with all whitespace removed.
     */
    private List<String> data;

    /**
     * TransformModel instance that is built.
     */
    private TransformModel model;

    /**
     * Class of formatting methods to support .rct data processing.
     */
    private StringFormatting formatter;

    /**
     * ID of the Imports component, -1 if it does not exist.
     */
    private int importsId = -1;

    /**
     * Integer storing the ID of the next component to be instantiated.
     */
    int nextId = 2;

    /**
     * Constructor for ComponentFactory class.
     * @param data
     * List of Strings containing .rct data, should be split by spaces and have all whitespace removed.
     */
    public ComponentFactory(List<String> data) {
        this.data = data;
        model = new TransformModel();
        parentStack = new Stack<>();
        parentStack.push(1);
        formatter = new StringFormatting();
    }

    public TransformModel getDataModel() {
        return model;
    }

    /**
     * Pops top value from parentIdStack, unless it is the root.
     * @throws RuntimeException
     * if the parentIdStack only contains 1, the root parent ID which cannot be removed.
     */
    public void attemptPopParentStack() {
        if (parentStack.size() == 1) {
            throw new RuntimeException("Too many '}' in input - cannot pop root from parent ID stack");
        }
        parentStack.pop();
    }

    public void pushToParentStack(int id) {
        parentStack.push(id);
    }

    /**
     * Method provides an ID to use from {@code nextId}, incrementing it each time.
     * @return int representing the next available unique ID.
     */
    private int useNextId() {
        nextId++;
        return (nextId-1);
    }

    /**
     * Updates {@code nextID} to account for the simultaneous instantiation of several components.
     * @param compsMade
     * int representing the number of components created, and the required value to increment {@code nextID} by.
     */
    private void incrementNextId(int compsMade) {
        nextId += compsMade;
    }

    /**
     * Method that updates TransformModel with the supplied component, and updates its parent
     * if it has one.
     * @param newComp
     * NamedComponent representing the new component to add to TransformModel.
     */
    private void updateModel(NamedComponent newComp) {
        model.addNewComp(newComp);
        if (newComp instanceof Reference) {
            // Adds reference's EventBoxes to the model.
            model.addNewComp(((Reference) newComp).getEventBoxes());
        }
        // Skip adding Child to parent if newComp is a root component or an StmBody (which is automatically added).
        if (!(newComp instanceof StmBody || parentStack.peek() == 1)) {
            NamedComponent parent = model.getCompById(newComp.getParentId());
            parent.addChild(newComp);
        }
    }

    // Component creation methods:

    /**
     * Creates either a TypeDec or RCPackage component and updates TransformModel.
     * @param name
     * String representing the component name
     * @param type
     * Either SimpleCompType.TYPE_DEC or SimpleCompType.RCPACKAGE, denotes component type to create.
     * @return 2 (indicating offset to increment parser by).
     */
    public int createNameOnlyComp(String name, SimpleCompType type) {
        NameOnlyComponent component = new NameOnlyComponent(this.useNextId(), name, type);
        updateModel(component);
        return 2;
    }

    /**
     * Creates an Imports component and updates TransformModel or, if an Imports component already exists, adds
     * {@code name} to its context.
     * @param name
     * String representing name of imported namespace.
     * @return 2 (indicating offset to increment parser by).
     */
    public int createImportComp(String name) {
        ContextComponent component;
        if (importsId != -1) {
            component = (ContextComponent) model.getCompById(importsId);
        } else {
            component = new ContextComponent(this.useNextId(), "imports", SimpleCompType.IMPORTS);
            importsId = component.getId();
            updateModel(component);
        }
        pushToParentStack(importsId);
        ContextData contextLine = new ContextData(this.useNextId(), name, importsId, ContextType.TEXT);
        updateModel(contextLine);
        attemptPopParentStack();
        return 2;
    }

    /**
     * Creates either an Enumeration or Record component and updates TransformModel.
     * @param start
     * int pointing to index of the name of the component in {@code data}.
     * @param type
     * specifies the type of component to be created, either SimpleCompType.ENUMERATION or SimpleCompType.RECORD.
     * @return offset to increment parser to starting index of next component.
     */
    public int createEnumRecord(int start, SimpleCompType type) {
        ContextComponent component = new ContextComponent(this.useNextId(), data.get(start), type);
        pushToParentStack(component.getId());
        int contextSize = (type==SimpleCompType.RECORD)? 3 : 1;
        int endIndex = formatter.findCompEnd(start);
        for (int i=start+2; i<endIndex; i+=contextSize) {
            String contextRowName = (type==SimpleCompType.RECORD)?
                    formatter.buildString(i, i+3, true) : data.get(i);
            ContextData contextLine = new ContextData(
                    this.useNextId(),
                    contextRowName,
                    parentStack.peek(),
                    ContextType.TEXT);
            updateModel(contextLine);
        }
        attemptPopParentStack();
        updateModel(component);
        return (2 + (start - endIndex));
    }

    /**
     * Creates a Junction component and updates TransformModel.
     * @param name
     * String specifying the name of the component.
     * @param type
     * Specifies the type of Junction, either JunctionType.DEFAULT, .INITIAL or .PROB.
     * @return offset to increment parser to starting index of next component.
     */
    public int createJunction(String name, JunctionType type) {
        Junction junction = new Junction(this.useNextId(), name, getStmBodyParentID(), type);
        updateModel(junction);
        return 2;
    }

    /**
     * Creates a Reference component, and any associated EventBoxes, and updates TransformModel.
     * @param name
     * String indicating the name of the component.
     * @param defName
     * String indicating the name of the component that this component references.
     * @return 4 (offset to increment parser to starting index of next component).
     */
    public int createRef(String name, String defName, boolean opRef) {
        NamedComponent referencedObj = (opRef)? model.getOpByName(defName) : model.getCompByName(defName);
        Reference ref = new Reference(this.useNextId(), name, parentStack.peek(), referencedObj);
        this.incrementNextId(ref.createEventBoxes().size());
        updateModel(ref);
        return 4;
    }

    /**
     * Creates either a new RCModule, Controller, RCInterface, State Machine or Robotic Platform component and
     * updates TransformModel.
     * @param name
     * String indicating name of the component.
     * @param pattern
     * String indicating the type of component to create, can be either "module", "controller", "interface", "stm",
     * or "robotic".
     * @return 3 or 4 (offset to increment parser to starting index of next component).
     */
    public int createModConIntRpStm(String name, String pattern) {
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
                component = new ContextComponent(
                        this.useNextId(),
                        name,
                        parentStack.peek(),
                        SimpleCompType.RCINTERFACE);
                break;
            case "stm":
                component = new StmComponent(this.useNextId(), name, parentStack.peek(), ComplexCompType.STM);
                this.incrementNextId(1); // For StmBody within STM.
                updateModel(((StmComponent) component).getBody());
                break;
            default:
                component = new ContextEventComponent(this.useNextId(), name, parentStack.peek(), ComplexCompType.RP);
                offsetToReturn = 4;
        }
        updateModel(component);
        this.pushToParentStack(component.getId());
        return offsetToReturn;
    }

    /**
     * Creates a Function component and updates TransformModel.
     * @param start
     * int pointing to the index of the start of the function's name.
     * @return offset to increment parser to starting index of next component.
     */
    public int createFunction(int start) {
        int nameEnd = formatter.findStringIndex(start, "{");
        String name = formatter.buildString(start, nameEnd, List.of(',', ':'));
        ContextComponent component = new ContextComponent(this.useNextId(), name, SimpleCompType.FUNCTION);
        updateModel(component);
        this.pushToParentStack(component.getId());
        return 1 + (nameEnd-start);
    }

    /**
     * Creates a State component and updates TransformModel.
     * @param name
     * String specifying the name of the component.
     * @param isFinal
     * boolean indicating whether the component is a final state.
     * @return 2 or 3 (offset to increment parser to starting index of next component).
     */
    public int createState(String name, boolean isFinal) {
        int offset = 2;
        State state = new State(this.useNextId(), name, getStmBodyParentID(), isFinal);
        updateModel(state);
        if (!isFinal) {
            this.incrementNextId(1); // For StmBody within state.
            updateModel(state.getBody());
            this.pushToParentStack(state.getId());
            offset = 3;
        }
        return offset;
    }

    /**
     * Creates an Operation component and updates TransformModel.
     * @param start
     * int pointing to the index of the start of the Operation's signature.
     * @return offset to increment parser to starting index of next component.
     */
    public int createOperation(int start) {
        int nameEnd = formatter.findStringIndex(start, "{");
        String name = formatter.buildString(start, nameEnd, List.of(',', ':'));
        if (data.subList(nameEnd, formatter.findStringIndex(nameEnd+1, "}")).contains("terminates")) {
            name += " [terminates]";
        }
        StmComponent operation = new StmComponent(
                this.useNextId(),
                name,
                parentStack.peek(),
                ComplexCompType.OPERATION);
        updateModel(operation);
        updateModel(operation.getBody());
        this.incrementNextId(1); //For StmBody within operation.
        this.pushToParentStack(operation.getId());
        return 1 + (nameEnd-start);
    }

    /**
     * Creates an Event context row, EventBox or both if required, and updates TransformModel.
     * @param start
     * int pointing to the index of the start of the component's name.
     * @return offset to increment parser to starting index of next component.
     */
    public int createEvent(int start) {
        int offset = 2;
        String name;
        if (data.get(start+1).equals(":")) {
            name = formatter.buildString(start, start+3, List.of(':'));
            offset = 4;
        } else {
            name = data.get(start);
        }
        if (model.getCompById(parentStack.peek()) instanceof ContextComponent) {
            ContextData event = new ContextData(this.useNextId(), name, parentStack.peek(), ContextType.EVENT);
            updateModel(event);
        } else {
            createEventBoxForParent(name, model.getCompById(parentStack.peek()));
        }
        return offset;
    }

    /**
     * Creates either a Required, Provided or Defined RCInterface context row (and adds EventBoxes to the parent
     * for Defined Interface), and updates TransformModel.
     * @param name
     * String specifiying the name of the interface.
     * @param type
     * Specifies the type of interface, either ContextType.D_INTERFACE, .R_INTERFACE or .P_INTERFACE.
     * @return 2 (offset to increment parser to starting index of next component).
     */
    public int createInterfaceRef(String name, ContextType type) {
        ContextData component;
        if (type == ContextType.D_INTERFACE) {
            ContextComponent referencedInterface = (ContextComponent) model.getCompByName(name);
            component = new DefInterfaceRef(this.useNextId(), name, parentStack.peek(), referencedInterface);
            for (ContextData event : referencedInterface.getEvents()) {
                createEventBoxForParent(event.getName(), model.getCompById(component.getParentId()));
            }
        } else {
            component = new ContextData(this.useNextId(), name, parentStack.peek(), type);
        }
        updateModel(component);
        return 2;
    }

    /**
     * Creates either a Variable or Constant context row and updates TransformModel.
     * @param start
     * int pointing to index of the start of the component.
     * @param type
     * either ContextType.VAR or .CONSTANT, specifies the type of component to create.
     * @return offset to increment parser to starting index of next component.
     */
    public int createVarConst(int start, ContextType type) {
        int end = start + 3;
        if (data.get(start+3).equals(",")) {
            for (int i=start+3; i < data.size(); i+=3) {
                if (!data.get(i).equals(",")) {
                    end = i - 3;
                    break;
                }
            }
        }
        ContextData component = new ContextData(
                this.useNextId(),
                formatter.buildString(start, end, true),
                parentStack.peek(),
                type);
        updateModel(component);
        return 1 + (end-start);
    }

    /**
     * Creates a Clock context row and updates TransformModel.
     * @param name
     * String specifying the name of the clock.
     * @return 2 (offset to increment parser to starting index of next component).
     */
    public int createClock(String name) {
        ContextData component = new ContextData(this.useNextId(), name, parentStack.peek(), ContextType.CLOCK);
        updateModel(component);
        return 2;
    }

    /**
     * Creates either a Precondition or Postcondition context row and updates TransformModel.
     * @param start
     * int pointing to index of the start of the condition.
     * @param type
     * specifies the type of component to create, either ContextType.POSTCONDITION or .PRECONDITION.
     * @return offset to increment parser to starting index of next component.
     */
    public int createPrePostCond(int start, ContextType type) {
        int end = formatter.findStringIndex(start,
                List.of("precondition", "postcondition", "}", "event", "provides",
                        "requires", "uses", "clock", "const", "var", "terminates"));
        String name = formatter.buildString(start, end, true);
        ContextData component = new ContextData(this.useNextId(), name, parentStack.peek(), type);
        updateModel(component);
        return 1 + (end-start);
    }

    /**
     * Creates a ContextData instance of type ContextType.TEXT to represent the Action of a State and
     * updates TransformModel.
     * @param start
     * int pointing to the index of the start of the component name.
     * @param type
     * specifies the type of action to create, either "entry", "during", or "exit".
     * @return offset to increment parser to starting index of next component.
     */
    public int createStateAction(int start, String type) {
        int end = formatter.findStringIndex(start,
                List.of("entry", "during", "exit", "state", "final", "initial", "probabilistic",
                        "junction", "transition"));
        String name = type + " " + formatter.buildString(start, end, List.of(',', ';'));
        ContextData action = new ContextData(this.useNextId(), name, parentStack.peek(), ContextType.TEXT);
        updateModel(action);
        return 1 + (end-start);
    }

    /**
     * Creates an Operation Signature (OpSig) context row and updates TransformModel.
     * @param start
     * int pointing to index of start of the signature.
     * @return offset to increment parser to starting index of next component.
     */
    public int createOpSig(int start) {
        int end = formatter.findStringIndex(start, ")");
        String name = formatter.buildString(start, end, List.of(',', ':'));
        ContextData component = new ContextData(this.useNextId(), name, parentStack.peek(), ContextType.OP_SIG);
        updateModel(component);
        return 1 + (start-end);
    }

    /**
     * Creates a Connection component and updates TransformModel.
     * @param start
     * int pointing to index of the name of the component.
     * @return offset to increment parser to starting index of next component.
     */
    public int createConnection(int start) {
        int offset = 8;
        int srcId = findEventBoxForConnect(data.get(start), data.get(start+2));
        int tgtId = findEventBoxForConnect(data.get(start+4), data.get(start+6));
        Connection connection = new Connection(this.useNextId(), getStmBodyParentID(), srcId, tgtId);
        switch (data.get(start+7)) {
            case "[mult]":
                connection.setBidi(true);
                offset = 9;
                break;
            case "(_async)":
                connection.makeAsync();
                offset = 9;
                if (data.get(start+8).equals("[mult]")) {
                    connection.setBidi(true);
                    offset = 10;
                }
        }
        updateModel(connection);
        return offset;
    }

    /**
     * Creates a Connection component representing a State Transition and updates TransformModel.
     * @param start
     * int pointing to index of the component's name.
     * @return offset to increment parser to starting index of next component.
     */
    public int createTransition(int start) {
        String name = data.get(start);
        int end = formatter.findStringIndex(start+1, "}");
        List<String> details = generateTransitionDetails(start + 2, end);
        Connection transition = new Connection(
                this.useNextId(),
                name,
                parentStack.peek(),
                Integer.parseInt(details.get(0)),
                Integer.parseInt(details.get(1)),
                details.get(2));
        updateModel(transition);
        return 1 + (end-start);
    }

    /**
     *Check if parent requires an EventBox for the event and add one if needed.
     * @param eventName
     * String representing name of event that may need an EventBox.
     * @param parent
     * NamedComponent representing parent to add EventBox to.
     */
    private void createEventBoxForParent(String eventName, NamedComponent parent) {
        if (parent instanceof ContextEventComponent) {
            EventBox eventBox = new EventBox(this.useNextId(), eventName, parent.getId());
            pushToParentStack(parent.getId());
            updateModel(eventBox);
            attemptPopParentStack();
        }
    }

    /**
     * Processes a Transition definition to find the transition source, target and its display label.
     * @param start
     * int pointing to index of the start of the transition definition (one after the opening brace: '{').
     * @param end
     * int pointing to index of the end of the transition definition (the closing brace: '}').
     * @return List of Strings containing the source ID, target ID and label of a transition, in that order.
     * @throws RuntimeException
     * if the transition definition is missing either the source or target ID.
     */
    private List<String> generateTransitionDetails(int start, int end) {
        StringBuilder label = new StringBuilder();
        String srcId = null;
        String tgtId = null;
        boolean inProb = false;
        boolean inCondition = false;
        List<String> syntax = List.of("from", "to", "trigger", "condition", "action", "send", "probability");
        for (int i=start; i<end; i++) {
            String current = data.get(i);
            if (syntax.contains(current)) {
                if (inCondition) {
                    label.append("]");
                    inCondition = false;
                } else if (inProb) {
                    label.append("}");
                    inProb = false;
                }
                switch (current) {
                    case "from":
                        srcId = data.get(i+1);
                        i++;
                        break;
                    case "to":
                        tgtId = data.get(i+1);
                        i++;
                        break;
                    case "probability":
                        label.append("p{");
                        inProb = true;
                        break;
                    case "condition":
                        label.append("[");
                        inCondition = true;
                        break;
                    case "action":
                        label.append("/");
                        break;
                    case "trigger", "send":
                        continue;
                    default:
                        label.append(current);
                }
            }
        }
        if (srcId == null) {
            throw new RuntimeException(String.format("Transition %s missing source", data.get(start-2)));
        } else if (tgtId == null) {
            throw new RuntimeException(String.format("Transition %s missing destination", data.get(start-2)));
        }
        return List.of(srcId, tgtId, label.toString());
    }

    /**
     * Finds the ID of the EventBox a connection references.
     * @param parentName
     * String representing the name of the parent component that contains the EventBox.
     * @param eventName
     * String representing the name of the event corresponding with the target EventBox.
     * @return int representing the ID of the EventBox.
     */
    private int findEventBoxForConnect(String parentName, String eventName) {
        NamedComponent parentComp = model.getCompByName(parentName);
        return ((ContextEventComponent) parentComp).getEventBoxWithName(eventName).getId();
    }

    /**
     * Finds the ID of the StmBody parent of a component.
     * @return int representing ID of the StmBody within the current parent component.
     */
    private int getStmBodyParentID() {
        StmComponent parentComponent = (StmComponent) model.getCompById(parentStack.peek());
        return parentComponent.getBody().getId();
    }

    private class StringFormatting {
        /**
         * Private inner class containing several methods to support ComponentFactory with handling and
         * formatting Strings.
         */
        public StringFormatting() {}

        /**
         * Builds and returns a string from {@code data} in an index range [start - end), can be space separated.
         * @param start
         * int pointing to the start index for the string.
         * @param end
         * int pointing to the end index for the string.
         * @param spaceSeparated
         * boolean specifying whether the returned string should have spaces separating each symbol.
         * @return String from the desired index range in {@code data}.
         */
        public String buildString(int start, int end, boolean spaceSeparated) {
            return formString(start, end, spaceSeparated);
        }

        /**
         * Builds and returns a space separated string from {@code data} in an index range [start : end).
         * @param start
         * int pointing to the start index for the string.
         * @param end
         * int pointing to the end index for the string.
         * @param spaceTriggers
         * List of Characters representing symbols which cause the method to insert a space in the string.
         * @return String from the desired index range in {@code data}, with spaces inserted after each
         * instance of a symbol from {@code spaceTriggers}.
         */
        public String buildString(int start, int end, List<Character> spaceTriggers) {
            return addSpacesToStr(buildString(start, end, false), spaceTriggers);
        }

        /**
         * Adds spaces to a String after occurences of specific symbols.
         * @param text
         * String that spaces are to be inserted into.
         * @param triggers
         * List of Characters representing symbols which should have a space inserted after them in the
         * returned string.
         * @return String containing a formatted version of {@code text} with spaces inserted after each instance
         * of a symbol from {@code triggers}.
         */
        public String addSpacesToStr(String text, List<Character> triggers) {
            StringBuilder textToChange = new StringBuilder(text);
            for (int i=0; i<textToChange.length(); i++) {
                if (triggers.contains(textToChange.charAt(i))) {
                    textToChange.insert(i+1, " ");
                }
            }
            return textToChange.toString();
        }

        /**
         * Method used by buildString(..) to form a string from {@code data} in range [start : end).
         * @param start
         * int pointing to the start index for the string.
         * @param end
         * int pointing to the end index for the string.
         * @param spaceSeparated
         * boolean specifying whether the returned string should have spaces separating each symbol.
         * @return String from the desired index range in {@code data}.
         */
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

        /**
         * Finds and returns the index of the string {@code toFind} in {@code data}.
         * @param start
         * int pointing to the starting index of the range to search in {@code data}.
         * @param toFind
         * String to find.
         * @return int pointing to the index of {@code toFind} in {@code data}.
         */
        public int findStringIndex(int start, String toFind) {
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

        /**
         * Finds and returns the index of the first instance of a string from {@code toFind} in {@code data}.
         * @param start
         * int pointing to the starting index of the range to search in {@code data}.
         * @param toFind
         * List of Strings to find.
         * @return int pointing to the index of the first instance of a string in {@code toFind} in {@code data}.
         */
        public int findStringIndex(int start, List<String> toFind) {
            int found = -1;
            for (int i=start; i<data.size(); i++) {
                if (toFind.contains(data.get(i))) {
                    found = i;
                    break;
                }
            }
            return found;
        }

        /**
         * Finds the index of the closing brace '}' of a component in {@code data}.
         * @param start
         * int pointing to the opening brace '{' of a component.
         * @return int pointing to the closing brace '}' of a component.
         */
        public int findCompEnd(int start) {
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
    }
}
