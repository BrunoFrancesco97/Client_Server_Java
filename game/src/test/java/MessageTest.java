import org.example.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageTest {
    @Test
    public void testConstructor1(){
        Message<String> m = new Message<>("test");
        Assertions.assertAll(
                ()->Assertions.assertNotNull(m.getOwner()),
                ()->Assertions.assertNotNull(m.getEvent()),
                ()->Assertions.assertNull(m.getMessage())
        );
    }
    public void testConstructor2(){
        Message<String> m = new Message<>("owner","event","message");
        Assertions.assertAll(
                ()->Assertions.assertNotNull(m.getOwner()),
                ()->Assertions.assertNotNull(m.getEvent()),
                ()->Assertions.assertNotNull(m.getMessage()),
                ()->Assertions.assertEquals("owner",m.getOwner()),
                ()->Assertions.assertEquals("event",m.getEvent()),
                ()->Assertions.assertEquals("message",m.getMessage())
        );
    }

    public void testSetter(){
        Message<String> m = new Message<>("test");
        Assertions.assertNotNull(m.getEvent());
        Assertions.assertNotNull(m.getMessage());
        m.setEvent("testEvent");
        Assertions.assertEquals("testEvent",m.getEvent());
        m.setMessage("testMessage");
        Assertions.assertEquals("testMessage",m.getMessage());
    }
}
