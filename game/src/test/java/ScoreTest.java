import org.example.model.Question;
import org.example.model.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreTest {
    @Test
    public void test(){
        Score s = new Score("fra");
        Assertions.assertAll(
                ()->Assertions.assertEquals("fra",s.name),
                ()->Assertions.assertEquals(0,s.questions.size()),
                ()->Assertions.assertFalse(s.isCompleted())
        );
        s.setCompleted(true);
        Assertions.assertTrue(s.isCompleted());
        Assertions.assertEquals(0,s.getNumberOfCorrectAnswers());
        Assertions.assertEquals(0,s.getNumberOfUnCorrectAnswers());
        Question q1 = new Question("test1","answer1");
        Question q2 = new Question("test2","answer2");
        Question q3 = new Question("test3","answer3");
        q1.checkAnswer("answer1");
        q2.checkAnswer("answer4");
        q3.checkAnswer("answer3");
        s.addQuestion(q1);
        s.addQuestion(q2);
        s.addQuestion(q3);
        Assertions.assertEquals(3,s.questions.size());
        System.out.println(s);
        Assertions.assertAll(
                ()->Assertions.assertEquals(2,s.getNumberOfCorrectAnswers()),
                ()->Assertions.assertEquals(1,s.getNumberOfUnCorrectAnswers()),
                ()->Assertions.assertTrue(s.containQuestion(new Question("test1","answer1"))),
                ()->Assertions.assertFalse(s.containQuestion(new Question("test4","answer4"))),
                ()->Assertions.assertNotNull(s.getQuestion(0)),
                ()->Assertions.assertNull(s.getQuestion(-1)),
                ()->Assertions.assertNull(s.getQuestion(5))
        );
    }
}
