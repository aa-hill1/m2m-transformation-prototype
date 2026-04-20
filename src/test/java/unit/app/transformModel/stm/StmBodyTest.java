package unit.app.transformModel.stm;

import org.example.app.transformModel.RCModule;
import org.example.app.transformModel.connection.Connection;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.stm.Junction;
import org.example.app.transformModel.stm.JunctionType;
import org.example.app.transformModel.stm.State;
import org.example.app.transformModel.stm.StmBody;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ensures that the StmBody class can be safely queried and manipulated.
 */
public class StmBodyTest {

    @Test
    public void testAddState() {
        StmBody body = new StmBody(3, "stm-stmBody", 2);
        State state = new State(4, "testState", 3, false);
        body.addChild(state);
        assertEquals("testState", body.getStates().getFirst().getName());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            body.addChild(state);
        });
        String expectedMsg = "Cannot add state testState to stmBody stm-stmBody twice";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testAddJunction() {
        StmBody body = new StmBody(3, "stm-stmBody", 2);
        Junction junction = new Junction(4, "i0", 3, JunctionType.INITIAL);
        body.addChild(junction);
        assertEquals("i0", body.getJunctions().getFirst().getName());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            body.addChild(junction);
        });
        String expectedMsg = "Cannot add junction i0 to stmBody stm-stmBody twice";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testAddTransition() {
        StmBody body = new StmBody(3, "stm-stmBody", 2);
        Connection transition = new Connection(4, "t1", 3, 5, 6, "exp");
        body.addChild(transition);
        assertEquals("t1", body.getTransitions().getFirst().getName());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            body.addChild(transition);
        });
        String expectedMsg = "Cannot add transition t1 to stmBody stm-stmBody twice";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testAddWrongChildType() {
        StmBody body = new StmBody(3, "stm-stmBody", 2);
        RCModule module = new RCModule(4, "testModule");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            body.addChild(module);
        });
        String expectedMsg = "Cannot make component testModule a child of stmBody stm-stmBody";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testGetChildren() {
        StmBody body = new StmBody(3, "stm-stmBody", 2);
        State state = new State(4, "f0", 3, true);
        body.addChild(state);
        Junction junction = new Junction(5, "p1", 3, JunctionType.PROB);
        body.addChild(junction);
        Connection transition = new Connection(6, "t1", 3, 5, 4, "exp");
        body.addChild(transition);
        Map<String, List<NamedComponent>> children = body.getChildren();
        assertEquals(2, children.get("components").size());
        assertEquals("f0", children.get("components").getFirst().getName());
        assertEquals("p1", children.get("components").getLast().getName());
        assertEquals(1, children.get("connections").size());
        assertEquals("t1", children.get("connections").getFirst().getName());
    }

    @Test
    public void testGetContainedComponentsCount() {
        StmBody body = new StmBody(3, "stm-stmBody", 2);
        assertEquals(0, body.getContainedComponentsCount());
        State state = new State(4, "testState", 3, false);
        state.addChild(new Junction(6, "j1", 5, JunctionType.DEFAULT));
        state.addChild(new Junction(7, "i0", 5, JunctionType.INITIAL));
        state.addChild(new State(8, "f0", 5, true));
        body.addChild(state);
        Junction junction = new Junction(5, "p1", 3, JunctionType.PROB);
        body.addChild(junction);
        Connection transition = new Connection(6, "t1", 3, 5, 4, "exp");
        body.addChild(transition);
        assertEquals(5, body.getContainedComponentsCount());
    }
}
