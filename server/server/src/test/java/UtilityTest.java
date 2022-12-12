import org.example.model.Player;
import org.example.model.Question;
import org.example.utils.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class UtilityTest {
    private static final String QUESTION_FILE = "./questions.txt";
    @Test
    public void testRandomness(){
        ArrayList<Integer> ints = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            ints.add(Utility.randomIDGenerator(Integer.MAX_VALUE-1));
        }
        Collections.sort(ints);
        for(int i = 0; i < ints.size() - 1; i++){
            Assertions.assertNotEquals(ints.get(i), ints.get(i+1));
        }
    }

    @Test
    public void testGet1(){
        Player p = new Player("fra");
        Assertions.assertEquals(0,p.questions.size());
        Utility.readQuestionsFromFile(QUESTION_FILE, p, 10);
        Assertions.assertEquals(10,p.questions.size());
        for(Object q : p.questions){
            Assertions.assertTrue(q instanceof Question);
        }
        for(Question q : p.questions){
            Assertions.assertAll(
                    ()->Assertions.assertNotNull(q.quest),
                    ()->Assertions.assertNotNull(q.correct),
                    ()->Assertions.assertNotNull(q.wrong1),
                    ()->Assertions.assertNotNull(q.wrong2),
                    ()->Assertions.assertNotNull(q.wrong3),
                    ()->Assertions.assertNotNull(q.wrong4)
            );
        }
    }

    @Test
    public void testGet2(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("fra1"));
        players.add(new Player("fra2"));
        players.add(new Player("fra3"));
        for(Player p : players){
            Assertions.assertEquals(0,p.questions.size());
        }
        ArrayList<Player> playersNew = Utility.readQuestionsFromFile(QUESTION_FILE, players, 10);
        for(Player p : playersNew){
            Assertions.assertEquals(10,p.questions.size());
            for(Question q : p.questions){
                Assertions.assertAll(
                        ()->Assertions.assertNotNull(q.quest),
                        ()->Assertions.assertNotNull(q.correct),
                        ()->Assertions.assertNotNull(q.wrong1),
                        ()->Assertions.assertNotNull(q.wrong2),
                        ()->Assertions.assertNotNull(q.wrong3),
                        ()->Assertions.assertNotNull(q.wrong4)
                );
            }
        }
    }

}
