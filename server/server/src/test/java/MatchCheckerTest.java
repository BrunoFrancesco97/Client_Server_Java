import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchCheckerTest {
    @Test
    public void test1(){
        MatchChecker mm = new MatchChecker();
        Assertions.assertAll(
                ()->Assertions.assertNull(mm.getQuestion()),
                ()->Assertions.assertNull(mm.getType()),
                ()->Assertions.assertNull(mm.getMatch()),
                ()->Assertions.assertNull(mm.getName()),
                ()->Assertions.assertFalse(mm.isGoingOn()),
                ()->Assertions.assertEquals(1,mm.getPosition())
        );

    }
    public void test2(){
        MatchChecker mm = new MatchChecker();
        mm.setType("friendly");
        mm.setQuestion(new Question("quest","answer"));
        mm.setPosition(3);
        mm.setGoingOn(true);
        mm.setName("fra");
        mm.setMatch("test");
        Assertions.assertAll(
                ()->Assertions.assertEquals("quest",mm.getQuestion().quest),
                ()->Assertions.assertEquals("answer",mm.getQuestion().answer),
                ()->Assertions.assertEquals("friendly",mm.getType()),
                ()->Assertions.assertEquals("test",mm.getMatch()),
                ()->Assertions.assertEquals("fra",mm.getName()),
                ()->Assertions.assertTrue(mm.isGoingOn()),
                ()->Assertions.assertEquals(3,mm.getPosition())
        );
    }
}
