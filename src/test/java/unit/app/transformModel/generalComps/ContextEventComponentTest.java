package unit.app.transformModel.generalComps;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.ContextEventComponent;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ensures that the ContextEventComponent class can be safely queried and manipulated.
 */
public class ContextEventComponentTest {

    @Test
    public void testGetEventBoxWithName() {
        ContextEventComponent comp = new ContextEventComponent(2, "testRp", ComplexCompType.RP);
        comp.addEventBox(new EventBox(3, "testEvent: type", 2));
        assertEquals("testEvent", comp.getEventBoxWithName("testEvent").getEventName());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            comp.getEventBoxWithName("fakeEvent");
        });
        String expectedMsg = "Cannot get event box with name fakeEvent from component testRp";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testAddContextChild() {
        ContextEventComponent comp = new ContextEventComponent(2, "testRp", ComplexCompType.RP);
        ContextData contextData = new ContextData(3, "testVar: type", 2, ContextType.VAR);
        comp.addChild(contextData);
        assertEquals("testVar: type", comp.getContext().getFirst().getName());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            comp.addChild(contextData);
        });
        String expectedMsg = "Cannot add context testVar: type to component testRp twice";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void addChildWrongType() {
        ContextEventComponent comp = new ContextEventComponent(2, "testRp", ComplexCompType.RP);
        Junction junction = new Junction(3, "j1", 4, JunctionType.DEFAULT);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            comp.addChild(junction);
        });
        String expectedMsg = "Cannot make component j1 a child of component testRp";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testGetChildren() {
        ContextEventComponent comp = new ContextEventComponent(2, "testRp", ComplexCompType.RP);
        comp.addChild(new EventBox(3, "testEvent: type", 2));
        comp.addChild(new ContextData(4, "testOp()", 2, ContextType.OP_SIG));
        Map<String, List<NamedComponent>> children = comp.getChildren();
        assertEquals(1, children.get("eventBox").size());
        assertEquals("testEvent", ((EventBox) children.get("eventBox").getFirst()).getEventName());
        assertEquals(1, children.get("context").size());
        assertEquals("testOp()", children.get("context").getFirst().getName());
    }
}
