import org.example.model.Player;
import org.example.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerQuestionTest {
    @Test
    public void playerAddQuestion(){
        Player p = new Player();
        Assertions.assertEquals(0,p.questions.size());
        p.addQuestion(new Question("Test1","Test1a"));
        Assertions.assertEquals(1,p.questions.size());
    }
    @Test
    public void playerClearQuestion(){
        Player p = new Player();
        p.addQuestion(new Question("Test1","Test1a"));
        p.addQuestion(new Question("Test1","Test1a"));
        p.addQuestion(new Question("Test1","Test1a"));
        p.clearQuestions();
        Assertions.assertEquals(0,p.questions.size());
    }
    @Test
    public void playerHasQuestion(){
        Player p = new Player();
        Assertions.assertFalse(p.hasQuestion());
        p.addQuestion(new Question("Test1","Test1a"));
        p.addQuestion(new Question("Test1","Test1a"));
        p.addQuestion(new Question("Test1","Test1a"));
        Assertions.assertTrue(p.hasQuestion());
    }

    @Test
    public void playerPickQuestion(){
        Player p = new Player();
        Assertions.assertEquals(0,p.questions.size());
        p.addQuestion(new Question("Test1","Test1a"));
        p.addQuestion(new Question("Test2","Test1a"));
        Question q = p.pickQuestion();
        Assertions.assertInstanceOf(Question.class, q);
    }
    @Test
    public void playerPopQuestion(){
        Player p = new Player();
        Assertions.assertEquals(0,p.questions.size());
        p.addQuestion(new Question("Test1","Test1a"));
        p.addQuestion(new Question("Test2","Test1a"));
        Question q = p.popQuestion();
        Assertions.assertAll(
                () -> Assertions.assertInstanceOf(Question.class,q),
                () -> Assertions.assertEquals(q.quest,"Test1"),
                () -> Assertions.assertEquals(1,p.questions.size())
        );
    }
    @Test
    public void playerEquals(){
        Player p1 = new Player("test1");
        Player p2 = new Player("test1");
        Player p3 = new Player("test2");
        Assertions.assertAll(
                () -> Assertions.assertTrue(p1.equals(p2)),
                () -> Assertions.assertTrue(p1.equals(p1)),
                () -> Assertions.assertFalse(p1.equals(p3)),
                () -> Assertions.assertFalse(p2.equals(p3))
        );
    }

    @Test
    public void playerID(){
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            players.add(new Player());
        }
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < players.size(); j++){
                int cont = 0;
                for(Player p : players){
                    if(players.get(j).getId() == p.getId())
                        cont++;
                }
                Assertions.assertEquals(1,cont);
            }
        }


        Player p1 = new Player("test1");
        Player p2 = new Player("test1");
        Player p3 = new Player("test2");
        Assertions.assertAll(
                () -> Assertions.assertTrue(p1.equals(p2)),
                () -> Assertions.assertTrue(p1.equals(p1)),
                () -> Assertions.assertFalse(p1.equals(p3)),
                () -> Assertions.assertFalse(p2.equals(p3))
        );
    }

    @Test
    public void playerReadynessFinishing(){
        Player p = new Player();
        Assertions.assertAll(
                () -> Assertions.assertFalse(p.isReady()),
                () -> Assertions.assertFalse(p.hasFinished)
        );
        p.setReady(true);
        p.hasFinished = true;
        Assertions.assertAll(
                () -> Assertions.assertTrue(p.isReady()),
                () -> Assertions.assertTrue(p.hasFinished)
        );
    }

    @Test
    public void playerIndex(){
        Player p = new Player();
        Assertions.assertEquals(-1, p.getIndexLastQuestion());
        p.setIndexLastQuestion(5);
        Assertions.assertEquals(5, p.getIndexLastQuestion());

    }
}
