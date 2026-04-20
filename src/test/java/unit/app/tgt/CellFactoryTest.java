package unit.app.tgt;

import org.example.app.tgt.CellFactory;
import org.example.app.transformModel.Controller;
import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.context.DefInterfaceRef;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.example.app.transformModel.stm.State;
import org.example.persistence.XmlTemplates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Class ensures that the methods for assembling specific components in CellFactory correctly call the createCell()
 * method (which handles the actual generation of the component in the target model as an mxCell). Class also ensures
 * that createCell() generates this component correctly when given appropriate input.
 */
public class CellFactoryTest {

    @Test
    public void testAssembleNameOnlyComp() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleNameOnlyComp(any(), any())).thenCallRealMethod();
        NameOnlyComponent comp = new NameOnlyComponent(1, "testPackage", SimpleCompType.RCPACKAGE);
        mockedFactory.assembleNameOnlyComp(comp, new String[]{"0", "0"});
        String[] expectedCompData = {"1", "testPackage", "0", "0"};
        String[] expectedTemplate = XmlTemplates.getPackage();
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleImportsComp() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleContextComp(any(), any())).thenCallRealMethod();
        ContextComponent comp = new ContextComponent(1, "imports", SimpleCompType.IMPORTS);
        mockedFactory.assembleContextComp(comp, new String[]{"0", "0"});
        String[] expectedCompData = {"1", "0", "0"};
        String[] expectedTemplate = XmlTemplates.getImports();
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void assembleContextComp() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleContextComp(any(), any())).thenCallRealMethod();
        ContextComponent comp = new ContextComponent(1, "testInterface", SimpleCompType.RCINTERFACE);
        mockedFactory.assembleContextComp(comp, new String[]{"0", "0"});
        String[] expectedCompData = {"1", "testInterface", "0", "0"};
        String[] expectedTemplate = XmlTemplates.getInterface();
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleModule() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleRCModule(any(), any())).thenCallRealMethod();
        RCModule module = new RCModule(1, "testModule");
        mockedFactory.assembleRCModule(module, new String[]{"0", "0"});
        String[] expectedCompData = {"1-0", "testModule", "0", "0", "1", "1-0"};
        String[] expectedTemplate = XmlTemplates.getModule();
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleJunction() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleJunction(any(), any())).thenCallRealMethod();
        Junction junction = new Junction(2, "p1", 1, JunctionType.PROB);
        mockedFactory.assembleJunction(junction, new String[]{"0", "0"});
        String[] expectedCompData = {"2", "1", "0", "0"};
        String[] expectedTemplate = XmlTemplates.getJunction(JunctionType.PROB);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleRef() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleRef(any(), any())).thenCallRealMethod();
        Controller controller = new Controller(1, "testCtlr");
        Reference reference = new Reference(2, "cref_0", 1, controller);
        mockedFactory.assembleRef(reference, new String[]{"0", "0"});
        String[] expectedCompData = {"2", "1", "testCtlr", "0", "0"};
        String[] expectedTemplate = XmlTemplates.getController(false);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleControllerDef() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleConRpDef(any(), any())).thenCallRealMethod();
        Controller controller = new Controller(2, "testCtlr");
        mockedFactory.assembleConRpDef(controller, new String[]{"0", "0"});
        String[] expectedCompData = {"2-0", "1", "testCtlr", "0", "0", null, "2", "2-0"};
        String[] expectedTemplate = XmlTemplates.getController(true);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleRpDef() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleConRpDef(any(), any())).thenCallRealMethod();
        ContextEventComponent rp = new ContextEventComponent(2, "testRp", ComplexCompType.RP);
        mockedFactory.assembleConRpDef(rp, new String[]{"0", "0"});
        String[] expectedCompData = {"2-0", "1", "testRp", "0", "0", null, "2", "2-0"};
        String[] expectedTemplate = XmlTemplates.getRp(true);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleOpStmDef() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleOpStmDef(any(), any())).thenCallRealMethod();
        StmComponent opDef = new StmComponent(2, "testOp(param: type)", ComplexCompType.OPERATION);
        mockedFactory.assembleOpStmDef(opDef, new String[]{"0", "0"});
        String[] expectedCompData = {"2", "1", "testOp(param: type)", "0", "0", null, "3", "2"};
        String[] expectedTemplate = XmlTemplates.getOperation(true);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleState() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleState(any(), any())).thenCallRealMethod();
        State state = new State(2, "testState", 1, false);
        mockedFactory.assembleState(state, new String[]{"0", "0"});
        String[] expectedCompData = {"2", "1", "testState", "0", "0", null, "3", "2"};
        String[] expectedTemplate = XmlTemplates.getState(false);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testAssembleFinalState() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.assembleState(any(), any())).thenCallRealMethod();
        State state = new State(2, "f0", 1, true);
        mockedFactory.assembleState(state, new String[]{"0", "0"});
        String[] expectedCompData = {"2", "1", "0", "0"};
        String[] expectedTemplate = XmlTemplates.getState(true);
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testCreateEventBoxes() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.createEventBoxes(any(), any(), anyFloat())).thenCallRealMethod();
        List<EventBox> boxes = new ArrayList<>();
        boxes.add(new EventBox(2, "eventOne: type", 1));
        boxes.add(new EventBox(3, "eventTwo: type", 1));
        boxes.add(new EventBox(4, "eventThree: type", 1));
        mockedFactory.createEventBoxes(boxes, "1", 1);
        String[] expectedTemplate = XmlTemplates.getEventBox();
        String[] expectedCompData = {"2", "1", "eventOne: type", "160", "0"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
        expectedCompData = new String[]{"3", "1", "eventTwo: type", "160", "25"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
        expectedCompData = new String[]{"4", "1", "eventThree: type", "160", "50"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    /**
     * Test that the createCell() method is called with the correct parameters for a bidirectional connection, an
     * asynchronous connection, and a connection that is both bidirectional and asynchronous.
     */
    @Test
    public void testCreateConnections() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.createConnections(any())).thenCallRealMethod();
        List<Connection> connections = new ArrayList<>();
        Connection connOne = new Connection(2, 1, 5, 6);
        connOne.setBidi(true);
        connections.add(connOne);
        Connection connTwo = new Connection(3, 1, 7, 8);
        connTwo.makeAsync();
        connections.add(connTwo);
        Connection connThree = new Connection(4, 1, 9, 10);
        connThree.makeAsync();
        connThree.setBidi(true);
        connections.add(connThree);
        mockedFactory.createConnections(connections);
        String[] expectedTemplate = XmlTemplates.getConnection(true);
        String[] expectedCompData = {"2", "1", "5", "6", "2-0", "2", ""};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
        expectedTemplate = XmlTemplates.getConnection(false);
        expectedCompData = new String[]{"3", "1", "7", "8", "3-0", "3", "2", "async"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
        expectedTemplate = XmlTemplates.getConnection(true);
        expectedCompData = new String[]{"4", "1", "9", "10", "4-0", "4", "async"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    @Test
    public void testCreateContext() {
        CellFactory mockedFactory = getFactoryMock();
        when(mockedFactory.createContext(any(), any())).thenCallRealMethod();
        List<ContextData> contextData = new ArrayList<>();
        contextData.add(new ContextData(2, "testEvent: type", 1, ContextType.EVENT));
        contextData.add(new DefInterfaceRef(3, "testInterface", 1, null));
        contextData.add(new ContextData(4, "testOp(param: type)", 1, ContextType.OP_SIG));
        mockedFactory.createContext(contextData, "1");
        String[] expectedTemplate = XmlTemplates.getEvent();
        String[] expectedCompData = {"2", "1", "testEvent: type"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
        expectedTemplate = XmlTemplates.getInterfaceRef(ContextType.D_INTERFACE);
        expectedCompData = new String[]{"3", "1", "testInterface"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
        expectedTemplate = XmlTemplates.getOpSig();
        expectedCompData = new String[]{"4", "1", "testOp(param: type)"};
        verify(mockedFactory).createCell(expectedTemplate, expectedCompData);
    }

    /**
     * Ensure that the createCell(String[] template, String[] data) method correctly combines template and component
     * data to produce the finished mxCell.
     */
    @Test
    public void testCellCreation() {
        CellFactory factory = new CellFactory();
        String[] packageData = {"2", "testPackage", "0", "0"};
        String generatedCell = factory.createCell(XmlTemplates.getPackage(), packageData);
        String expectedCell = """
                
                \t<mxCell id="2" parent="1" style="html=1;whiteSpace=wrap;fontStyle=5" \
                value="package testPackage" vertex="1">
                \t\t<mxGeometry height="30" width="110" x="0" \
                y="0" as="geometry" />
                \t</mxCell>""";
        assertEquals(expectedCell, generatedCell);
    }

    /**
     * Creates and returns a mocked instance of CellFactory, where the createCell() method has been mocked.
     * @return mocked instance of CellFactory.
     */
    private CellFactory getFactoryMock() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.createCell(any(), any())).thenReturn("1");
        return mockedFactory;
    }
}
