package unit.app.transformModel;

import org.example.app.transformModel.Controller;
import org.example.app.transformModel.Reference;
import org.example.app.transformModel.connection.EventBox;
import org.example.app.transformModel.context.ContextData;
import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.generalComps.ComplexCompType;
import org.example.app.transformModel.generalComps.NamedComponent;
import org.example.app.transformModel.generalComps.StmComponent;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensure that the Reference class can be safely queried and manipulated.
 */
public class ReferenceTest {

    @Test
    public void testCreateEventBoxesWithNone() {
        Controller objToRef = new Controller(2, "testCtlr");
        Reference reference = new Reference(3, "cref_0", objToRef);
        List<EventBox> boxes = reference.createEventBoxes();
        assertTrue(boxes.isEmpty());
    }

    @Test
    public void testCreateEventBoxes() {
        StmComponent objToRef = new StmComponent(2, "testStm", ComplexCompType.STM);
        objToRef.addChild(new EventBox(3, "eventOne: type", 2));
        objToRef.addChild(new EventBox(4, "eventTwo: ( type )", 2));
        objToRef.addChild(new EventBox(5, "eventThree", 2));
        Reference reference = new Reference(6, "sref_0", objToRef);
        List<EventBox> boxes = reference.createEventBoxes();
        assertEquals(3, boxes.size());
        String[] eventNames = {"eventOne: type", "eventTwo: ( type )", "eventThree"};
        for (int i=0; i<3; i++) {
            EventBox box = boxes.get(i);
            assertEquals(i+7, box.getId());
            assertEquals(6, box.getParentId());
            assertEquals(eventNames[i], box.getName());
        }
    }

    @Test
    public void addChildWrongType() {
        Reference reference = new Reference(2, "rref_0", null);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reference.addChild(new ContextData(3, "exit exp...exp", 2, ContextType.TEXT));
        });
        String expectedMsg = "Cannot make component exit exp...exp a child of reference rref_0";
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void testGetChildren() {
        StmComponent objToRef = new StmComponent(2, "testOp()", ComplexCompType.OPERATION);
        objToRef.addChild(new EventBox(3, "eventOne: type", 2));
        objToRef.addChild(new EventBox(4, "eventTwo: ( type )", 2));
        objToRef.addChild(new EventBox(5, "eventThree", 2));
        Reference reference = new Reference(6, "opref_0", objToRef);
        reference.createEventBoxes();
        Map<String, List<NamedComponent>> children = reference.getChildren();
        assertEquals(3, children.get("eventBox").size());
        assertEquals("eventOne", ((EventBox) children.get("eventBox").getFirst()).getEventName());
        assertEquals("eventTwo", ((EventBox) children.get("eventBox").get(1)).getEventName());
        assertEquals("eventThree", ((EventBox) children.get("eventBox").getLast()).getEventName());
    }
}
