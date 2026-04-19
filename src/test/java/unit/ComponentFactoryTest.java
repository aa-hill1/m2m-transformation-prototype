package unit;

import org.example.app.src.ComponentFactory;
import org.example.app.transformModel.Controller;
import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.example.app.transformModel.stm.State;
import org.example.app.transformModel.stm.StmBody;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensure that ComponentFactory class correctly creates all types of RoboChart components as their transformation model
 * equivalent.
 */
public class ComponentFactoryTest {

    @Test
    public void testParentStackHandling() {
        ComponentFactory factory = new ComponentFactory(null);
        factory.pushToParentStack(2);
        factory.attemptPopParentStack();
        Exception exception = assertThrows(RuntimeException.class, factory::attemptPopParentStack);
        String expectedMsg = "Too many '}' in input - cannot pop root from parent ID stack";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testNameOnlyComponentCreation() {
        ArrayList<String> data = new ArrayList<>(List.of("...", "type", "testType", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        factory.createNameOnlyComp(data.get(2), SimpleCompType.TYPE_DEC);
        // Check component is correct:
        NamedComponent comp = checkBasicCompFeatures(factory, "testType", 2, 1);
        assertInstanceOf(NameOnlyComponent.class, comp);
        assertEquals(SimpleCompType.TYPE_DEC, ((NameOnlyComponent) comp).getType());
    }

    @Test
    public void testImportCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "import", "namespaceOne", "...", "import", "namespaceTwo"));
        ComponentFactory factory = new ComponentFactory(data);
        factory.createImportComp(data.get(2));
        factory.createImportComp(data.get(5));
        // Check only one Import component has been created:
        assertEquals(1, factory.getDataModel().getRootComps().size());
        // Check component is correct:
        NamedComponent comp = checkBasicContextCompFeatures(
                factory, "imports", 2, 1, SimpleCompType.IMPORTS, 2);
        // Check context data is correct:
        ContextData importOne = ((ContextComponent) comp).getContext().getFirst();
        assertEquals(2, importOne.getParentId());
        assertEquals("namespaceOne", importOne.getName());
        assertEquals(ContextType.TEXT, importOne.getType());
        ContextData importTwo = ((ContextComponent) comp).getContext().get(1);
        assertEquals(2, importTwo.getParentId());
        assertEquals("namespaceTwo", importTwo.getName());
        assertEquals(ContextType.TEXT, importTwo.getType());
    }

    @Test
    public void testEmptyEnumRecordCreation() {
        ArrayList<String> data = new ArrayList<>(List.of("...", "enumeration", "testEnum", "{", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createEnumRecord(2, SimpleCompType.ENUMERATION);
        assertEquals(4, offset);
        // Check component correct:
        NamedComponent comp = checkBasicContextCompFeatures(
                factory, "testEnum", 2, 1, SimpleCompType.ENUMERATION, 0);
        // Check context is empty:
        assertEquals(0, ((ContextComponent) comp).getContext().size());
    }

    @Test
    public void testEnumRecordCreationWithContext() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "record", "testRecord", "{", "testData", ":", "type", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createEnumRecord(2, SimpleCompType.RECORD);
        assertEquals(7, offset);
        // Check component correct:
        NamedComponent comp = checkBasicContextCompFeatures(
                factory, "testRecord", 2, 1, SimpleCompType.RECORD, 1);
        assertEquals(1, ((ContextComponent) comp).getContext().size());
        // Check context data correct:
        ContextData recordData = ((ContextComponent) comp).getContext().getFirst();
        assertEquals(2, recordData.getParentId());
        assertEquals("testData : type", recordData.getName());
        assertEquals(ContextType.TEXT, recordData.getType());
    }

    @Test
    public void testModuleCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "module", "testModule", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createModConIntRpStm(data.get(2), "module");
        assertEquals(3, offset);
        // Check component correct:
        NamedComponent comp = checkBasicCompFeatures(factory, "testModule", 2, 1);
        assertInstanceOf(RCModule.class, comp);
    }

    @Test
    public void testControllerCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "controller", "testCtlr", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createModConIntRpStm(data.get(2), "controller");
        assertEquals(3, offset);
        // Check component correct:
        NamedComponent comp = checkBasicContextEventCompFeatures(
                factory, "testCtlr", 2, 1, ComplexCompType.CONTROLLER);
    }

    @Test
    public void testInterfaceCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createModConIntRpStm(data.get(2), "interface");
        assertEquals(3, offset);
        // Check component correct:
        NamedComponent comp = checkBasicContextCompFeatures(
                factory, "testInterface", 2, 1, SimpleCompType.RCINTERFACE, 0);
    }

    @Test
    public void testRpCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "robotic", "platform", "testRp", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createModConIntRpStm(data.get(3), "robotic");
        assertEquals(4, offset);
        // Check component correct:
        NamedComponent comp = checkBasicContextEventCompFeatures(
                factory, "testRp", 2, 1, ComplexCompType.RP);
    }

    @Test
    public void testStmCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "stm", "testStm", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createModConIntRpStm(data.get(2), "stm");
        assertEquals(3, offset);
        // Check component correct:
        NamedComponent comp =
                checkBasicStmCompFeatures(factory, "testStm", 2, 1, ComplexCompType.STM);
    }

    @Test
    public void testFunctionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "function", "testFunc", "(", "param", ":", "type", ")", ":", "type",
                        "{", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createFunction(2);
        assertEquals(10, offset);
        // Check component correct:
        NamedComponent comp = checkBasicContextCompFeatures(
                factory, "testFunc(param: type): type", 2, 1, SimpleCompType.FUNCTION, 0);
    }

    @Test
    public void testOperationCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "operation", "testOp(", "param", ":", "real", ")", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createOperation(2);
        assertEquals(7, offset);
        // Check component correct:
        NamedComponent comp = checkBasicStmCompFeatures(
                factory, "testOp(param: real)", 2, 1, ComplexCompType.OPERATION);
    }

    @Test
    public void testOperationCreationForTerminates() {
        ArrayList<String> data = new ArrayList<>(List.of(
                "...", "operation", "testOp(", "param", ":", "real", ")", "{", "...", "terminates",
                "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Check returned offset correct:
        int offset = factory.createOperation(2);
        assertEquals(7, offset);
        // Check component correct:
        NamedComponent comp = checkBasicStmCompFeatures(
                factory, "testOp(param: real) [terminates]", 2, 1, ComplexCompType.OPERATION);
    }

    @Test
    public void testFinalStateCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "stm", "testStm", "{", "final", "f1", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent component for state:
        factory.createModConIntRpStm("testStm", "stm");
        // Check returned offset correct:
        int offset = factory.createState("f1", true);
        assertEquals(2, offset);
        // Check component correct:
        NamedComponent comp = checkBasicStmCompFeatures(
                factory, "f1", 4, 3, ComplexCompType.STATE, true);
    }

    @Test
    public void testStateCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "stm", "testStm", "{", "state", "testState", "{", "...", "}", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent component for state:
        factory.createModConIntRpStm("testStm", "stm");
        // Check returned offset correct:
        int offset = factory.createState("testState", false);
        assertEquals(3, offset);
        // Check component correct:
        NamedComponent comp = checkBasicStmCompFeatures(
                factory, "testState", 4, 3, ComplexCompType.STATE);
    }

    @Test
    public void testStateCreationWithNoParent() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "state", "testState", "{", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            factory.createState("testState", false);
        });
        String expectedMsg = "Error - state or junction not defined within a valid parent component.";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testJunctionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "stm", "testStm", "{", "probabilistic", "p1", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent component for state:
        factory.createModConIntRpStm("testStm", "stm");
        // Check returned offset correct:
        int offset = factory.createJunction("p1", JunctionType.PROB);
        assertEquals(2, offset);
        // Check component correct:
        NamedComponent comp = checkBasicCompFeatures(factory, "p1", 4, 3);
        assertInstanceOf(Junction.class, comp);
        assertEquals(JunctionType.PROB, ((Junction) comp).getType());
    }

    @Test
    public void testEventCreationForContextRow() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "event", "testEvent", ":", "type", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent component for event:
        factory.createModConIntRpStm("testInterface", "interface");
        // Check returned offset correct:
        int offset = factory.createEvent(5);
        assertEquals(4, offset);
        // Check component correct:
        NamedComponent comp = checkContextDataFeatures(
                factory, "testEvent: type", 3, 2, ContextType.EVENT);
    }

    @Test
    public void testEventCreationForEventBox() {
        ArrayList<String> data = new ArrayList<>(List.of(
                "...", "controller", "testCtlr", "{", "event", "testEvent", ":", "(", "defType",
                ")", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent component for event:
        factory.createModConIntRpStm("testCtlr", "controller");
        // Check returned offset correct:
        int offset = factory.createEvent(5);
        assertEquals(6, offset);
        // Check component correct:
        NamedComponent comp = checkEventBoxFeatures(
                factory, "testEvent: (defType)", "testEvent", 3, 2);
    }

    @Test
    public void testRefCreation() {
        ArrayList<String> data = new ArrayList<>(List.of(
                "...", "controller", "testCtlr", "{", "event", "testEvent", ":", "type",
                "}", "cref", "c_ref0", "=", "testCtlr", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Create controller definition for reference:
        factory.createModConIntRpStm("testCtlr", "controller");
        factory.createEvent(5);
        factory.attemptPopParentStack();
        // Check component correct:
        factory.createRef("c_ref0", "testCtlr", false);
        NamedComponent comp = checkBasicContextEventCompFeatures(
                factory, "c_ref0", 4, 1, ComplexCompType.REF);
        assertEquals("testCtlr", ((Reference) comp).getReferencedObj().getName());
        assertEquals("testEvent", ((Reference) comp).getEventBoxes().getFirst().getEventName());
        // Check Event box formed correctly:
        checkEventBoxFeatures(factory, "testEvent: type", "testEvent", 5, 4);
    }

    @Test
    public void testOpRefCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "operation", "testOp(", "param", ":", "real", ")", "{", "...", "}", "...",
                        "opref", "op_ref0", "=", "testOp"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create operation definition for reference:
        factory.createOperation(2);
        factory.attemptPopParentStack();
        // Check component correct:
        factory.createRef("op_ref0", "testOp", true);
        NamedComponent comp = checkBasicContextEventCompFeatures(
                factory, "op_ref0", 4, 1, ComplexCompType.REF);
        assertEquals("testOp(param: real)", ((Reference) comp).getReferencedObj().getName());
    }

    @Test
    public void testDefInterfaceRefCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "testEvent", ":", "type", "}", "...",
                        "controller", "testCtlr", "{", "uses", "testInterface", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Create interface and controller definition to reference it:
        factory.createModConIntRpStm("testInterface", "interface");
        factory.createEvent(4);
        factory.attemptPopParentStack();
        factory.createModConIntRpStm("testCtlr", "controller");
        // Check component correct:
        factory.createInterfaceRef("testInterface", ContextType.D_INTERFACE);
        checkContextDataFeatures(factory, "testInterface", 5, 4, ContextType.D_INTERFACE);
        // Check that parent has been updated with new Event box:
        checkEventBoxFeatures(factory, "testEvent: type", "testEvent", 6, 4);
    }

    @Test
    public void testReqInterfaceRefCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "...", "}", "...",
                        "robotic", "platform", "testRp", "{", "requires", "testInterface", "...", "}", "..."));
        ComponentFactory factory = new ComponentFactory(data);
        // Create interface and controller definition to reference it:
        factory.createModConIntRpStm("testInterface", "interface");
        factory.attemptPopParentStack();
        factory.createModConIntRpStm("testRp", "robotic");
        // Check component correct:
        factory.createInterfaceRef("testInterface", ContextType.R_INTERFACE);
        checkContextDataFeatures(factory, "testInterface", 4, 3, ContextType.R_INTERFACE);
    }

    @Test
    public void testVarCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "stm", "testStm", "{", "var", "varOne", ":", "type", ",", "varTwo", ":",
                        "type", ",", "varThree", ":", "type", ",", "varFour", ":", "type", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent STM for context data:
        factory.createModConIntRpStm("testStm", "stm");
        // Check returned offset correct:
        int offset = factory.createVarConst(5, ContextType.VAR);
        assertEquals(16, offset);
        // Check component correct:
        checkContextDataFeatures(
                factory, "varOne : type , varTwo : type , varThree : type , varFour : type", 4,
                2, ContextType.VAR);
    }

    @Test
    public void testConstCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "robotic", "platform", "testRp", "{", "const", "constOne", ":", "type",
                        "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent RP for context data:
        factory.createModConIntRpStm("testRp", "robotic");
        // Check returned offset correct:
        int offset = factory.createVarConst(6, ContextType.CONSTANT);
        assertEquals(4, offset);
        // Check component correct:
        checkContextDataFeatures(factory, "constOne : type", 3, 2, ContextType.CONSTANT);
    }

    @Test
    public void testClockCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "clock", "testC", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent interface for context data:
        factory.createModConIntRpStm("testInterface", "interface");
        // Check component correct:
        factory.createClock("testC");
        checkContextDataFeatures(factory, "testC", 3, 2, ContextType.CLOCK);
    }

    @Test
    public void testPostPreconditionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "function", "testFunc", "(", "param", ":", "type", ")", ":", "type",
                        "{", "precondition", "...", "exp", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent function for context data:
        factory.createFunction(2);
        // Check returned offset correct:
        int offset = factory.createPrePostCond(12, ContextType.PRECONDITION);
        assertEquals(4, offset);
        // Check component correct:
        checkContextDataFeatures(factory, "... exp ...", 3, 2, ContextType.PRECONDITION);
    }

    @Test
    public void testActionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("stm", "testStm", "{", "...", "state", "testState", "{", "...", "entry", "...",
                        "exp", "...", "}", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent function for context data:
        factory.createModConIntRpStm("testStm", "stm");
        factory.createState("testState", false);
        // Check returned offset correct:
        int offset = factory.createStateAction(9, "entry");
        assertEquals(4, offset);
        // Check component correct:
        checkContextDataFeatures(factory, "entry ...exp...", 6, 4, ContextType.TEXT);
    }

    @Test
    public void testOpSigCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "testOpSig()", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent interface for context data:
        factory.createModConIntRpStm("testInterface", "interface");
        // Check returned offset correct:
        int offset = factory.createOpSig(4, true);
        assertEquals(1, offset);
        // Check component correct:
        checkContextDataFeatures(factory, "testOpSig()", 3, 2, ContextType.OP_SIG);
    }

    @Test
    public void testOpSigCreationWithParams() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "interface", "testInterface", "{", "testOpSig(", "paramOne", ":", "type",
                        ",", "paramTwo", ":", "type)", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent interface for context data:
        factory.createModConIntRpStm("testInterface", "interface");
        // Check returned offset correct:
        int offset = factory.createOpSig(4, false);
        assertEquals(8, offset);
        // Check component correct:
        checkContextDataFeatures(
                factory, "testOpSig(paramOne: type, paramTwo: type)", 3, 2, ContextType.OP_SIG);
    }

    @Test
    public void testBidiConnectionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "module", "testModule", "{", "controller", "testCtlrOne", "{", "event",
                        "testEvent", ":", "type", "}", "controller", "testCtlrTwo", "{", "event", "testEvent", ":",
                        "type", "}", "connection", "testCtlrOne", "on", "testEvent", "to", "testCtlrTwo", "on",
                        "testEvent", "[mult]", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent, source and target for connection:
        factory.createModConIntRpStm("testModule", "module");
        factory.createModConIntRpStm("testCtlrOne", "controller");
        factory.createEvent(8);
        factory.attemptPopParentStack();
        factory.createModConIntRpStm("testCtlrTwo", "controller");
        factory.createEvent(16);
        factory.attemptPopParentStack();
        // Check returned offset correct:
        int offset = factory.createConnection(21);
        assertEquals(9, offset);
        // Check component has been added to parent:
        assertEquals(
                "4-to-6",
                ((RCModule) factory.getDataModel().getCompById(2)).getConnections().getFirst().getName());
        // Check component correct:
        checkConnectionFeatures(factory, "4-to-6", 7, 2, 4, 6, true, "");
    }

    @Test
    public void testAsyncConnectionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "module", "testModule", "{", "controller", "testCtlrOne", "{", "event",
                        "testEvent", ":", "type", "}", "controller", "testCtlrTwo", "{", "event", "testEvent", ":",
                        "type", "}", "connection", "testCtlrOne", "on", "testEvent", "to", "testCtlrTwo", "on",
                        "testEvent", "(", "async_", ")", "[mult]", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent, source and target for connection:
        factory.createModConIntRpStm("testModule", "module");
        factory.createModConIntRpStm("testCtlrOne", "controller");
        factory.createEvent(8);
        factory.attemptPopParentStack();
        factory.createModConIntRpStm("testCtlrTwo", "controller");
        factory.createEvent(16);
        factory.attemptPopParentStack();
        // Check returned offset correct:
        int offset = factory.createConnection(21);
        assertEquals(12, offset);
        // Check component has been added to parent:
        assertEquals(
                "4-to-6",
                ((RCModule) factory.getDataModel().getCompById(2)).getConnections().getFirst().getName());
        // Check component correct:
        checkConnectionFeatures(factory, "4-to-6", 7, 2, 4, 6, true, "async");
    }

    @Test
    public void testTransitionCreation() {
        ArrayList<String> data = new ArrayList<>(
                List.of("...", "stm", "testStm", "{", "initial", "i0", "junction", "j1",
                        "transition", "t0", "{", "from", "i0", "to", "j1", "trigger", "communication", "condition",
                        "exp", "...", "exp", "action", "exp", "exp", "...", "}", "...", "}"));
        ComponentFactory factory = new ComponentFactory(data);
        // Create parent, source and target for connection:
        factory.createModConIntRpStm("testStm", "stm");
        factory.createJunction("i0", JunctionType.INITIAL);
        factory.createJunction("j1", JunctionType.DEFAULT);
        // Check returned offset correct:
        int offset = factory.createTransition(9);
        assertEquals(18, offset);
        // Check component has been added to parent:
        assertEquals(
                "t0",
                ((StmBody) factory.getDataModel().getCompById(3)).getTransitions().getFirst().getName());
        // Check component correct:
        checkConnectionFeatures(
                factory, "t0", 6, 2, 4, 5, false,
                "communication[exp...exp]/expexp...");
    }

    /**
     * Test that a given Connection instance's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @param src
     * integer representing the component's expected source ID.
     * @param tgt
     * integer representing the component's expected source ID.
     * @param bidi
     * boolean representing whether the component should be bidirectional or not.
     * @param label
     * string representing the expected contents of the component's label.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkConnectionFeatures(ComponentFactory factory,
                                                   String name,
                                                   int id,
                                                   int parentId,
                                                   int src,
                                                   int tgt,
                                                   boolean bidi,
                                                   String label) {
        NamedComponent comp = factory.getDataModel().getCompById(id);
        assertInstanceOf(Connection.class, comp);
        assertEquals(id, comp.getId());
        assertEquals(parentId, comp.getParentId());
        assertEquals(name, comp.getName());
        assertEquals(src, ((Connection) comp).getSrc());
        assertEquals(tgt, ((Connection) comp).getTgt());
        assertEquals(bidi, ((Connection) comp).isBidi());
        assertEquals(label, ((Connection) comp).getLabel());
        return comp;
    }

    /**
     * Test that a given EventBox instance's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param eventName
     * string representing the expected eventName of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkEventBoxFeatures(ComponentFactory factory,
                                                 String name,
                                                 String eventName,
                                                 int id,
                                                 int parentId) {
        // Check Event box has been added to its parent:
        ContextEventComponent parent = (ContextEventComponent) factory.getDataModel().getCompById(parentId);
        assertEquals(parent.getEventBoxWithName(eventName).getId(), id);
        // Check component is correct:
        NamedComponent comp = factory.getDataModel().getCompById(id);
        assertInstanceOf(EventBox.class, comp);
        assertEquals(name, comp.getName());
        assertEquals(parentId, comp.getParentId());
        assertEquals(eventName, ((EventBox) comp).getEventName());
        return comp;
    }

    /**
     * Test that a given ContextData instance's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @param type
     * ContextType representing the component's expected type.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkContextDataFeatures(ComponentFactory factory,
                                                    String name,
                                                    int id,
                                                    int parentId,
                                                    ContextType type) {
        // Check component has been added to parent's context:
        NamedComponent parent = factory.getDataModel().getCompById(parentId);
        if (parent instanceof ContextComponent) {
            assertEquals(id, ((ContextComponent) parent).getContext().getFirst().getId());
        } else {
            assertEquals(id, ((ContextEventComponent) parent).getContext().getFirst().getId());
        }
        // Check component correct:
        NamedComponent comp = factory.getDataModel().getCompById(id);
        assertEquals(name, comp.getName());
        assertEquals(parentId, comp.getParentId());
        assertInstanceOf(ContextData.class, comp);
        assertEquals(type, ((ContextData) comp).getType());
        return comp;
    }

    /**
     * Method overload with the same parameters, excluding {@code isFinalState}, which calls
     * the original method with {@code isFinalState} = false.
     */
    private NamedComponent checkBasicStmCompFeatures(ComponentFactory factory,
                                                     String name,
                                                     int id,
                                                     int parentId,
                                                     ComplexCompType type) {
        return checkBasicStmCompFeatures(factory, name, id, parentId, type, false);
    }

    /**
     * Test that a given StmComponent instance's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @param type
     * ComplexCompType representing the component's expected type.
     * @param isFinalState
     * boolean that is true if the component to test is a final state, otherwise is false.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkBasicStmCompFeatures(ComponentFactory factory,
                                                     String name,
                                                     int id,
                                                     int parentId,
                                                     ComplexCompType type,
                                                     boolean isFinalState) {
        NamedComponent comp = checkBasicContextEventCompFeatures(factory, name, id, parentId, type);
        assertEquals(type, ((StmComponent) comp).getType());
        if (type == ComplexCompType.STATE) {
            assertInstanceOf(State.class, comp);
            assertEquals(isFinalState, ((State) comp).isFinalState());
        } else {
            assertInstanceOf(StmComponent.class, comp);
        }
        if (!isFinalState) {
            // Check StmBody correct:
            StmBody body = ((StmBody) factory.getDataModel().getCompById(id+1));
            assertEquals(id, body.getParentId());
            assertEquals((name+"-stmBody"), body.getName());
        }
        return comp;
    }

    /**
     * Test that a given ContextEventComponent instance's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @param type
     * ComplexCompType representing the component's expected type.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkBasicContextEventCompFeatures(ComponentFactory factory,
                                                              String name,
                                                              int id,
                                                              int parentId,
                                                              ComplexCompType type) {
        NamedComponent comp = checkBasicCompFeatures(factory, name, id, parentId);
        assertEquals(type, ((ContextEventComponent) comp).getType());
        switch (type) {
            case CONTROLLER -> assertInstanceOf(Controller.class, comp);
            case REF -> assertInstanceOf(Reference.class, comp);
            default -> assertInstanceOf(ContextEventComponent.class, comp);
        }
        return comp;
    }

    /**
     * Test that a given ContextComponent instance's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @param type
     * SimpleCompType representing the component's expected type.
     * @param contextSize
     * integer representing the expected size of the component's context.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkBasicContextCompFeatures(ComponentFactory factory,
                                                         String name,
                                                         int id,
                                                         int parentId,
                                                         SimpleCompType type,
                                                         int contextSize) {
        NamedComponent comp = checkBasicCompFeatures(factory, name, id, parentId);
        assertInstanceOf(ContextComponent.class, comp);
        assertEquals(type, ((ContextComponent) comp).getType());
        assertEquals(contextSize, ((ContextComponent) comp).getContext().size());
        return comp;
    }

    /**
     * Test that a given component's basic features are correct.
     * @param factory
     * ComponentFactory instance that created the component to test.
     * @param name
     * string representing the expected name of the component.
     * @param id
     * integer representing the component's expected ID.
     * @param parentId
     * integer representing the component's expected parent ID.
     * @return a NamedComponent instance of the component.
     */
    private NamedComponent checkBasicCompFeatures(ComponentFactory factory, String name, int id, int parentId) {
        NamedComponent comp = factory.getDataModel().getCompByName(name);
        assertEquals(id, comp.getId());
        assertEquals(parentId, comp.getParentId());
        return comp;
    }
}
