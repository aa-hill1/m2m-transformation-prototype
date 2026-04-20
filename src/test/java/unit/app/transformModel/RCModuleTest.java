package unit.app.transformModel;

import org.example.app.transformModel.Controller;
import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.connection.Connection;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ensures that the RCModule class can be safely queried and manipulated.
 */
public class RCModuleTest {

    @Test
    public void testAddAndGetChildren() {
        RCModule module = new RCModule(2, "testModule");
        module.addChild(new Connection(3, 2, 4, 5));
        module.addChild(new Controller(4, "testCtlr", 2));
        module.addChild(new StmComponent(5, "testOp()", 2, ComplexCompType.OPERATION));
        module.addChild(new StmComponent(7, "testStm", 2, ComplexCompType.STM));
        module.addChild(new ContextEventComponent(9, "testRp", 2, ComplexCompType.RP));
        module.addChild(new Reference(10, "rref_0", 2, null));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            module.addChild(new Junction(11, "j1", 2, JunctionType.DEFAULT));
        });
        String expectedMsg = "Cannot make component j1 a child of RCModule testModule";
        assertEquals(expectedMsg, exception.getMessage());
        Map<String, List<NamedComponent>> children = module.getChildren();
        assertEquals(3, children.get("connections").getFirst().getId());
        assertEquals(5, children.get("components").size());
        for (NamedComponent comp : children.get("components")) {
            String nameComparison;
            switch (comp) {
                case Reference reference -> nameComparison = "rref_0";
                case Controller controller -> nameComparison = "testCtlr";
                case StmComponent stmComponent -> {
                    if (stmComponent.getType() == ComplexCompType.OPERATION) {
                        nameComparison = "testOp()";
                    } else {
                        nameComparison = "testStm";
                    }
                }
                default -> nameComparison = "testRp";
            }
            assertEquals(nameComparison, comp.getName());
        }
    }

    @Test
    public void testGetContainedComponentsCount() {
        RCModule module = new RCModule(2, "testModule");
        assertEquals(0, module.getContainedComponentsCount());
        module.addChild(new Connection(3, 2, 4, 5));
        Controller controller = new Controller(4, "testCtlr", 2);
        controller.addChild(new StmComponent(11, "testStmOne", 4, ComplexCompType.STM));
        module.addChild(controller);
        StmComponent operation = new StmComponent(5, "testOp()", 2, ComplexCompType.OPERATION);
        operation.addChild(new State(12, "testState", 5, false));
        module.addChild(operation);
        module.addChild(new StmComponent(7, "testStmTwo", 2, ComplexCompType.STM));
        module.addChild(new ContextEventComponent(9, "testRp", 2, ComplexCompType.RP));
        module.addChild(new Reference(10, "rref_0", 2, null));
        assertEquals(7, module.getContainedComponentsCount());
    }
}
