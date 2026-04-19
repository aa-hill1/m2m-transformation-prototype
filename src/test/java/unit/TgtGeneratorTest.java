package unit;

import org.example.app.tgt.CellFactory;
import org.example.app.tgt.TgtGenerator;
import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.example.app.transformModel.stm.State;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Class ensures that TgtGenerator can correctly recognise each type of component when traversing TransformModel, and
 * call the correct CellFactory method to transform it.
 */
public class TgtGeneratorTest {

    @Test
    public void testNameOnlyComp() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleNameOnlyComp(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        NameOnlyComponent comp = new NameOnlyComponent(1, "testPackage", SimpleCompType.RCPACKAGE);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleNameOnlyComp(comp, new String[]{"0", "0"});
        comp = new NameOnlyComponent(1, "testTypeDec", SimpleCompType.TYPE_DEC);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleNameOnlyComp(comp, new String[]{"0", "0"});
    }

    @Test
    public void testContextComp() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleContextComp(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        ContextComponent comp = new ContextComponent(1, "imports", SimpleCompType.IMPORTS);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleContextComp(comp, new String[]{"0", "0"});
        comp = new ContextComponent(1, "testFunction", SimpleCompType.FUNCTION);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleContextComp(comp, new String[]{"0", "0"});
    }

    @Test
    public void testRCModule() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleRCModule(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        RCModule comp = new RCModule(1, "testModule");
        generator.transformComponent(comp);
        verify(mockedFactory).assembleRCModule(comp, new String[]{"0", "0"});
    }

    @Test
    public void testJunction() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleJunction(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        Junction comp = new Junction(2, "testInitial", 1, JunctionType.INITIAL);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleJunction(comp, new String[]{"0", "0"});
        comp = new Junction(2, "testJunction", 1, JunctionType.DEFAULT);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleJunction(comp, new String[]{"0", "0"});
        comp = new Junction(2, "testProbJunc", 1, JunctionType.PROB);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleJunction(comp, new String[]{"0", "0"});
    }

    @Test
    public void testRef() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleRef(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        Reference comp = new Reference(1, "opref_0", null);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleRef(comp, new String[]{"0", "0"});
    }

    @Test
    public void testRpCtlrDef() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleConRpDef(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        ContextEventComponent comp = new ContextEventComponent(1, "testRp", ComplexCompType.CONTROLLER);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleConRpDef(comp, new String[]{"0", "0"});
        comp = new ContextEventComponent(2, "testCtlr", ComplexCompType.CONTROLLER);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleConRpDef(comp, new String[]{"0", "0"});
    }

    @Test
    public void testStmOpDef() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleOpStmDef(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        StmComponent comp = new StmComponent(1, "testStm", ComplexCompType.STM);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleOpStmDef(comp, new String[]{"0", "0"});
        comp = new StmComponent(2, "testOp", ComplexCompType.OPERATION);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleOpStmDef(comp, new String[]{"0", "0"});
    }

    @Test
    public void testStateDef() {
        CellFactory mockedFactory = mock(CellFactory.class);
        when(mockedFactory.assembleState(any(), any())).thenReturn("1");
        TgtGenerator generator = new TgtGenerator(mockedFactory);
        State comp = new State(2, "testState", 1, false);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleState(comp, new String[]{"0", "0"});
        comp = new State(3, "f0", 1, true);
        generator.transformComponent(comp);
        verify(mockedFactory).assembleState(comp, new String[]{"0", "0"});
    }
}
