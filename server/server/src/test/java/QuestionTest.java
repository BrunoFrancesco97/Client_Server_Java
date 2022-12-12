import org.example.model.Player;
import org.example.model.Question;
import org.example.utils.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class QuestionTest {
    private static final String QUESTION_FILE = "./questions.txt";
    @Test
    public void countQuestions(){
        Player p = new Player();
        Utility.readQuestionsFromFile(QUESTION_FILE, p, 5);
        Assertions.assertEquals(5,p.questions.size());
    }

    @Test
    public void checkEquivalence(){
        Question q1 = new Question("testQ","testA");
        Question q2 = new Question("testQ","testA");
        Question q3 = new Question("testA","testQ");
        Assertions.assertAll(
                ()->Assertions.assertTrue(q1.equals(q2)),
                ()->Assertions.assertTrue(q1.equals(q1)),
                ()->Assertions.assertFalse(q1.equals(q3)),
                ()->Assertions.assertFalse(q2.equals(q3))
        );
    }


    @Test
    public void checkNull(){
        Player p = new Player();
        Utility.readQuestionsFromFile(QUESTION_FILE, p, 5);
        Question q = p.popQuestion();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(q.wrong1),
                () -> Assertions.assertNotNull(q.wrong2),
                () -> Assertions.assertNotNull(q.wrong3),
                () -> Assertions.assertNotNull(q.wrong4),
                () -> Assertions.assertNotNull(q.correct)
        );
    }

    @Test
    public void getAnswers(){
        Player p = new Player();
        Utility.readQuestionsFromFile(QUESTION_FILE, p, 5);
        for(int i = 0; i < p.questions.size(); i++){
            Assertions.assertInstanceOf(ArrayList.class,p.questions.get(i).getAllAnswers());
        }
    }


    @Test
    public void checkAnswer(){
        Player p = new Player();
        Utility.readQuestionsFromFile(QUESTION_FILE, p, 5);
        Question q = p.popQuestion();
        Assertions.assertAll(
                () -> Assertions.assertTrue(q.checkAnswer(q.answer)),
                () -> Assertions.assertFalse(q.checkAnswer(q.wrong1)),
                () -> Assertions.assertFalse(q.checkAnswer(q.wrong2)),
                () -> Assertions.assertFalse(q.checkAnswer(q.wrong3)),
                () -> Assertions.assertFalse(q.checkAnswer(q.wrong4)),
                () -> Assertions.assertTrue(q.checkAnswer(q.answer.toLowerCase())),
                () -> Assertions.assertTrue(q.checkAnswer(q.answer.toUpperCase()))
        );
    }
}
