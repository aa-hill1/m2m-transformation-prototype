package unit;

import org.example.app.src.ComponentFactory;
import org.example.app.src.SrcParser;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.SimpleCompType;
import org.example.app.transformModel.stm.JunctionType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Class tests that SrcParser can recognise each type of RoboChart component and pass the correct data to its factory
 * class, ComponentFactory.
 */
public class SrcParserTest {

    @Test
    public void testNameOnlyComp() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createNameOnlyComp(any(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("package", "testPackage", "type", "testType"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createNameOnlyComp("testPackage", SimpleCompType.RCPACKAGE);
        parser.patternMatch(2);
        verify(mockedFactory).createNameOnlyComp("testType", SimpleCompType.TYPE_DEC);
    }

    @Test
    public void testImport() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createImportComp(any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("import", "testNamespace"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createImportComp("testNamespace");
    }

    @Test
    public void testEnumRecordDatatype() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createEnumRecord(anyInt(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("enumeration", "testEnum", "record", "testRecord", "datatype", "testDatatype"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createEnumRecord(1, SimpleCompType.ENUMERATION);
        parser.patternMatch(2);
        verify(mockedFactory).createEnumRecord(3, SimpleCompType.RECORD);
        parser.patternMatch(4);
        verify(mockedFactory).createEnumRecord(5, SimpleCompType.RECORD);
    }

    @Test
    public void testJunctions() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createJunction(any(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("junction", "j1", "initial", "i0", "probabilistic", "p0"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createJunction("j1", JunctionType.DEFAULT);
        parser.patternMatch(2);
        verify(mockedFactory).createJunction("i0", JunctionType.INITIAL);
        parser.patternMatch(4);
        verify(mockedFactory).createJunction("p0", JunctionType.PROB);
    }

    @Test
    public void testStates() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createState(any(), anyBoolean())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("state", "testState", "final", "f0"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createState("testState", false);
        parser.patternMatch(2);
        verify(mockedFactory).createState("f0", true);
    }

    @Test
    public void testRp() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createModConIntRpStm(any(), any())).thenReturn(1);
        when(mockedFactory.createRef(any(), any(), anyBoolean())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("robotic", "platform", "testRp", "rref", "rp_ref0", "=", "testRp"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createModConIntRpStm("testRp", "robotic");
        parser.patternMatch(3);
        verify(mockedFactory).createRef("rp_ref0", "testRp", false);
    }

    @Test
    public void testController() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createModConIntRpStm(any(), any())).thenReturn(1);
        when(mockedFactory.createRef(any(), any(), anyBoolean())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("controller", "testCtlr", "cref", "c_ref0", "=", "testCtlr"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createModConIntRpStm("testCtlr", "controller");
        parser.patternMatch(2);
        verify(mockedFactory).createRef("c_ref0", "testCtlr", false);
    }

    @Test
    public void testStm() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createModConIntRpStm(any(), any())).thenReturn(1);
        when(mockedFactory.createRef(any(), any(), anyBoolean())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("stm", "testStm", "sref", "s_ref0", "=", "testStm"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createModConIntRpStm("testStm", "stm");
        parser.patternMatch(2);
        verify(mockedFactory).createRef("s_ref0", "testStm", false);
    }

    @Test
    public void testOp() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createOperation(anyInt())).thenReturn(1);
        when(mockedFactory.createRef(any(), any(), anyBoolean())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("operation", "testOp(", "testOne", ":", "real)", "opref", "op_ref0", "=", "testOp"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createOperation(1);
        parser.patternMatch(5);
        verify(mockedFactory).createRef("op_ref0", "testOp", true);
    }

    @Test
    public void testModule() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createModConIntRpStm(any(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("module", "testModule"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createModConIntRpStm("testModule", "module");
    }

    @Test
    public void testInterface() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createModConIntRpStm(any(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("interface", "testInterface"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createModConIntRpStm("testInterface", "interface");
    }

    @Test
    public void testFunction() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createFunction(anyInt())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("function", "testFunc", "(", "param", ":", "real", ")", ":", "real"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createFunction(1);
    }

    @Test
    public void testConnectionTransition() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createConnection(anyInt())).thenReturn(1);
        when(mockedFactory.createTransition(anyInt())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("connection", "src", "on", "srcEvent", "to", "tgt", "on", "tgtEvent",
                        "transition", "t1", "{", "from", "src", "to", "tgt", "}"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createConnection(1);
        parser.patternMatch(8);
        verify(mockedFactory).createTransition(9);
    }

    @Test
    public void testEvent() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createEvent(anyInt())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("event", "testEvent", ":", "type"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createEvent(1);
    }

    @Test
    public void interfaceRef() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createInterfaceRef(any(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("provides", "testProvInterface", "requires", "testReqInterface", "uses", "testDefInterface"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createInterfaceRef("testProvInterface", ContextType.P_INTERFACE);
        parser.patternMatch(2);
        verify(mockedFactory).createInterfaceRef("testReqInterface", ContextType.R_INTERFACE);
        parser.patternMatch(4);
        verify(mockedFactory).createInterfaceRef("testDefInterface", ContextType.D_INTERFACE);
    }

    @Test
    public void testConstantVar() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createVarConst(anyInt(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(
                List.of("var", "testVar", ":", "type", "const", "testConst", ":", "type"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createVarConst(1, ContextType.VAR);
        parser.patternMatch(4);
        verify(mockedFactory).createVarConst(5, ContextType.CONSTANT);
    }

    @Test
    public void testClock() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createClock(any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("clock", "testClock"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createClock("testClock");
    }

    @Test
    public void testPrePostconditions() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createPrePostCond(anyInt(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("precondition", "exp", "postcondition", "exp"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createPrePostCond(1, ContextType.PRECONDITION);
        parser.patternMatch(2);
        verify(mockedFactory).createPrePostCond(3, ContextType.POSTCONDITION);
    }

    @Test
    public void testActions() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createStateAction(anyInt(), any())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("entry", "exp", "during", "exp", "exit", "exp"));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createStateAction(1, "entry");
        parser.patternMatch(2);
        verify(mockedFactory).createStateAction(3, "during");
        parser.patternMatch(4);
        verify(mockedFactory).createStateAction(5, "exit");
    }

    @Test
    public void testOpSig() {
        ComponentFactory mockedFactory = mock(ComponentFactory.class);
        when(mockedFactory.createOpSig(anyInt(), anyBoolean())).thenReturn(1);
        ArrayList<String> data = new ArrayList<>(List.of("opSigOne()", "opSigTwo", "(", ".."));
        SrcParser parser = new SrcParser(data, mockedFactory);
        parser.patternMatch(0);
        verify(mockedFactory).createOpSig(0, true);
        parser.patternMatch(1);
        verify(mockedFactory).createOpSig(1, false);
    }
}
