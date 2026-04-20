package unit.app.transformModel;

import org.example.app.transformModel.Controller;
import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.StmComponent;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.example.app.transformModel.stm.State;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ensure that the Controller class can be safely queried and manipulated.
 */
public class ControllerTest {

    @Test
    public void testAddAndGetChildren() {
        Controller controller = new Controller(2, "testCtlr");
        controller.addChild(new Connection(3, 2, 4, 5));
        controller.addChild(new ContextData(4, "testInterface", 2, ContextType.P_INTERFACE));
        controller.addChild(new StmComponent(5, "testOp()", 2, ComplexCompType.OPERATION));
        controller.addChild(new StmComponent(7, "testStm", 2, ComplexCompType.STM));
        controller.addChild(new EventBox(9, "testEvent: type", 2));
        controller.addChild(new Reference(10, "rref_0", 2, null));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.addChild(new Junction(11, "j1", 2, JunctionType.DEFAULT));
        });
        String expectedMsg = "Cannot make component j1 a child of controller testCtlr";
        assertEquals(expectedMsg, exception.getMessage());
        Map<String, List<NamedComponent>> children = controller.getChildren();
        assertEquals(3, children.get("connections").getFirst().getId());
        assertEquals(4, children.get("context").getFirst().getId());
        assertEquals(9, children.get("eventBox").getFirst().getId());
        assertEquals(3, children.get("components").size());
        for (NamedComponent comp : children.get("components")) {
            String nameComparison;
            if (comp instanceof StmComponent ) {
                if (((StmComponent) comp).getType() == ComplexCompType.OPERATION) {
                    nameComparison = "testOp()";
                } else {
                    nameComparison = "testStm";
                }
            } else {
                nameComparison = "rref_0";
            }
            assertEquals(nameComparison, comp.getName());
        }
    }

    @Test
    public void testGetContainedComponentsCount() {
        Controller controller = new Controller(2, "testCtlr");
        assertEquals(0, controller.getContainedComponentsCount());
        controller.addChild(new Connection(3, 2, 4, 5));
        controller.addChild(new ContextData(4, "testInterface", 2, ContextType.P_INTERFACE));
        StmComponent operation = new StmComponent(5, "testOp()", 2, ComplexCompType.OPERATION);
        operation.addChild(new State(12, "testState", 5, false));
        controller.addChild(operation);
        controller.addChild(new StmComponent(7, "testStm", 2, ComplexCompType.STM));
        controller.addChild(new EventBox(9, "testEvent: type", 2));
        controller.addChild(new Reference(10, "rref_0", 2, null));
        assertEquals(4, controller.getContainedComponentsCount());
    }
}
