package unit.app.transformModel.generalComps;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.StmComponent;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ensures that StmComponent instances can be safely queried and manipulated.
 */
public class StmComponentTest {

    @Test
    public void testAddChildren() {
        StmComponent comp = new StmComponent(2,"testStm", ComplexCompType.STM);
        comp.addChild(new ContextData(4, "testInterface", 2, ContextType.R_INTERFACE));
        comp.addChild(new EventBox(5, "testEvent: type", 3));
        comp.addChild(new Junction(6, "j1", 3, JunctionType.DEFAULT));
        assertEquals("testInterface", comp.getContext().getFirst().getName());
        assertEquals("testEvent", comp.getEventBoxWithName("testEvent").getEventName());
        assertEquals("j1", comp.getBody().getJunctions().getFirst().getName());
    }

    @Test
    public void testGetChildren() {
        StmComponent comp = new StmComponent(2,"testOpDef", ComplexCompType.OPERATION);
        comp.addChild(new ContextData(4, "testInterface", 2, ContextType.R_INTERFACE));
        comp.addChild(new EventBox(5, "testEvent: type", 3));
        comp.addChild(new Junction(6, "j1", 3, JunctionType.DEFAULT));
        Map<String, List<NamedComponent>> children = comp.getChildren();
        assertEquals(1, children.get("components").size());
        assertEquals("testOpDef-stmBody", children.get("components").getFirst().getName());
        assertEquals(1, children.get("context").size());
        assertEquals("testInterface", children.get("context").getFirst().getName());
        assertEquals(1, children.get("eventBox").size());
        assertEquals("testEvent", ((EventBox) children.get("eventBox").getFirst()).getEventName());
    }
}
