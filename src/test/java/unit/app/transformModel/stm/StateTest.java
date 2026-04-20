package unit.app.transformModel.stm;

import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.stm.State;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensures that the State class can be safely queried and manipulated.
 */
public class StateTest {

    @Test
    public void testStateInit() {
        State state = new State(2, "testState", 1, false);
        assertEquals(new ArrayList<>(), state.getContext());
        assertNull(state.getEventBoxes());
        assertEquals("testState-stmBody", state.getBody().getName());
        assertEquals(3, state.getBody().getId());
        assertEquals(2, state.getBody().getParentId());
    }

    @Test
    public void testFinalStateInit() {
        State finalState = new State(2, "f0", 1, true);
        assertNull(finalState.getContext());
        assertNull(finalState.getEventBoxes());
        assertNull(finalState.getBody());
        assertNull(finalState.getChildren());
        assertEquals(0, finalState.getContainedComponentsCount());
    }

    @Test
    public void testAddContextToState() {
        State state = new State(2, "testState", 1, false);
        state.addContextLine(new ContextData(4, "entry exp", 2, ContextType.TEXT));
        assertEquals("entry exp", state.getContext().getFirst().getName());
    }

    @Test
    public void testAddWrongContextToState() {
        State state = new State(2, "testState", 1, false);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            state.addContextLine(new ContextData(4, "testOp()", 2, ContextType.OP_SIG));
        });
        String expectedMsg = "Cannot add context testOp() to testState, must an Action of type ContextType.TEXT";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testAddChildrenToFinalState() {
        State finalState = new State(2, "f0", 1, true);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            finalState.addContextLine(new ContextData(4, "testClock", 2, ContextType.CLOCK));
        });
        String expectedMsg = "Cannot add context testClock to final state f0";
        assertEquals(expectedMsg, exception.getMessage());
        exception = assertThrows(RuntimeException.class, () -> {
            finalState.addChild(new EventBox(5, "testEvent", 2));
        });
        expectedMsg = "Cannot make component testEvent a child of final state f0";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testStateGetChildren() {
        State state = new State(2, "testState", 1, false);
        state.addChild(new ContextData(4, "during exp..", 2, ContextType.TEXT));
        state.addChild(new State(5, "f0", 3, true));
        Map<String, List<NamedComponent>> children = state.getChildren();
        assertNull(children.get("eventBox"));
        assertEquals(1, children.get("context").size());
        assertEquals("during exp..", children.get("context").getFirst().getName());
        assertEquals(1, children.get("components").size());
        assertEquals("testState-stmBody", children.get("components").getFirst().getName());
    }
}
