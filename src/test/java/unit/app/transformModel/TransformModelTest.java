package unit.app.transformModel;

import org.example.app.transformModel.Controller;
import org.example.app.transformModel.TransformModel;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.context.DefInterfaceRef;
import org.example.app.transformModel.generalComps.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensures that the transformation model can be safely queried and manipulated by calling methods from TransformModel.
 */
public class TransformModelTest {

    @Test
    public void testAddRootComp() {
        NameOnlyComponent comp = new NameOnlyComponent(2, "testTypeDec", SimpleCompType.TYPE_DEC);
        TransformModel model = new TransformModel();
        model.addNewComp(comp);
        assertEquals(1, model.getRootComps().size());
        assertEquals(comp, model.getCompById(2));
        assertEquals(comp, model.getCompByName("testTypeDec"));
    }

    @Test
    public void testAddNonRootComp() {
        ContextEventComponent comp = new ContextEventComponent(3, "testRp", 2, ComplexCompType.RP);
        TransformModel model = new TransformModel();
        model.addNewComp(comp);
        assertTrue(model.getRootComps().isEmpty());
        assertEquals(comp, model.getCompById(3));
        assertEquals(comp, model.getCompByName("testRp"));
    }

    /**
     * Test that EventBox and ContextData instances, which do not have unique names, are not added to TransformModel's
     * component-name map.
     */
    @Test
    public void testAddEventBoxAndContext() {
        TransformModel model = new TransformModel();
        DefInterfaceRef contextData = new DefInterfaceRef(3, "testInterface", 2, null);
        model.addNewComp(contextData);
        assertEquals(contextData, model.getCompById(3));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            model.getCompByName("testInterface");
        });
        String expectedMsg = "Error - please ensure components are defined in order of use, could not find component " +
                "with name testInterface";
        assertEquals(expectedMsg, exception.getMessage());
        EventBox box = new EventBox(4, "testEvent: type", 2);
        model.addNewComp(box);
        assertEquals(box, model.getCompById(4));
        exception = assertThrows(RuntimeException.class, () -> {
            model.getCompByName("testEvent: type");
        });
        expectedMsg = "Error - please ensure components are defined in order of use, could not find component " +
                "with name testEvent: type";
        assertEquals(expectedMsg, exception.getMessage());
        assertTrue(model.getRootComps().isEmpty());
    }

    @Test
    public void testGetOpName() {
        TransformModel model = new TransformModel();
        Controller ctlr = new Controller(3, "testOp_Ctlr");
        model.addNewComp(ctlr);
        ContextData contextData = new ContextData(4, "testOp", 3, ContextType.TEXT);
        model.addNewComp(contextData);
        StmComponent opDef = new StmComponent(2, "testOp(param: type)", ComplexCompType.OPERATION);
        model.addNewComp(opDef);
        assertEquals(opDef, model.getOpByName("testOp"));
    }

    @Test
    public void getOpNameWhenNotExist() {
        TransformModel model = new TransformModel();
        Controller ctlr = new Controller(3, "testOp_Ctlr");
        model.addNewComp(ctlr);
        ContextData contextData = new ContextData(4, "testOp", 3, ContextType.TEXT);
        model.addNewComp(contextData);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            model.getOpByName("testOp");
        });
        String expectedMsg = "Error - cannot find operation with name testOp";
    }
}
