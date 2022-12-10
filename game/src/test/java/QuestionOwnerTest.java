import org.example.model.Question;
import org.example.model.QuestionOwner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuestionOwnerTest {
    @Test
    public void test(){
        QuestionOwner q = new QuestionOwner(new Question("test1","answer1"),"fra");
        Assertions.assertAll(
                ()->Assertions.assertEquals("test1",q.getQuest().quest),
                ()->Assertions.assertEquals("answer1",q.getQuest().answer),
                ()->Assertions.assertEquals("fra",q.getP())
        );
        q.setP("bru");
        Assertions.assertEquals("bru",q.getP());
        q.setQuest(new Question("test2","answer2"));
        Assertions.assertEquals("test2",q.getQuest().quest);
        Assertions.assertEquals("answer2",q.getQuest().answer);
    }
}
