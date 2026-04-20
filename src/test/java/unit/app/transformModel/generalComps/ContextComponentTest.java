package unit.app.transformModel.generalComps;

import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.*;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ensures that the ContextComponent class can be safely queried and manipulated.
 */
public class ContextComponentTest {

    @Test
    public void testGetEvents() {
        ContextComponent comp = new ContextComponent(2, "testInterface", SimpleCompType.RCINTERFACE);
        comp.addContextLine(new ContextData(3, "testConst: type", 2, ContextType.CONSTANT));
        comp.addContextLine(new ContextData(4, "eventOne: type", 2, ContextType.EVENT));
        comp.addContextLine(new ContextData(5, "testClock", 2, ContextType.CLOCK));
        comp.addContextLine(new ContextData(6, "eventTwo", 2, ContextType.EVENT));
        List<ContextData> events = comp.getEvents();
        assertEquals(2, events.size());
        assertEquals("eventOne: type", events.getFirst().getName());
        assertEquals("eventTwo", events.getLast().getName());
    }

    @Test
    public void testAddChild() {
        ContextComponent comp = new ContextComponent(2, "testInterface", SimpleCompType.RCINTERFACE);
        ContextData contextData = new ContextData(3, "testVar: type", 2, ContextType.VAR);
        comp.addChild(contextData);
        assertEquals("testVar: type", comp.getContext().getFirst().getName());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            comp.addChild(contextData);
        });
        String expectedMsg = "Cannot add context testVar: type to component testInterface twice";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void addChildWrongType() {
        ContextComponent comp = new ContextComponent(2, "testEnum", SimpleCompType.ENUMERATION);
        Junction junction = new Junction(3, "j1", 4, JunctionType.DEFAULT);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            comp.addChild(junction);
        });
        String expectedMsg = "Cannot make component j1 a child of component testEnum";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testGetChildren() {
        ContextComponent comp = new ContextComponent(2, "testInterface", SimpleCompType.RCINTERFACE);
        comp.addChild(new ContextData(3, "testClock", 2, ContextType.CLOCK));
        comp.addChild(new ContextData(4, "testOp()", 2, ContextType.OP_SIG));
        Map<String, List<NamedComponent>> children = comp.getChildren();
        assertEquals(2, children.get("context").size());
        assertEquals("testClock", children.get("context").getFirst().getName());
        assertEquals("testOp()", children.get("context").getLast().getName());
    }
}
