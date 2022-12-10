import org.example.model.Match;
import org.example.model.Message;
import org.example.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MatchTest {

    @Test
    public void matchConstructor1(){
        Match m = new Match();
        Assertions.assertAll(
                ()->Assertions.assertNotNull(m.getPlayers()),
                ()->Assertions.assertEquals(0,m.getPlayers().size()),
                ()->Assertions.assertFalse(m.isAvailable()),
                ()->Assertions.assertNull(m.getType()),
                ()->Assertions.assertNull(m.getHost()),
                ()->Assertions.assertEquals(0,m.getSize()),
                ()->Assertions.assertEquals(0,m.getTime()),
                ()->Assertions.assertEquals(0,m.getNumberQuestions())
        );
    }

    @Test
    public void matchConstructor2(){
        Match m = new Match("friendly",new Player("test"));
        Assertions.assertAll(
                ()->Assertions.assertNotNull(m.getPlayers()),
                ()->Assertions.assertEquals(0,m.getPlayers().size()),
                ()->Assertions.assertFalse(m.isAvailable()),
                ()->Assertions.assertEquals("friendly",m.getType()),
                ()->Assertions.assertEquals("test",m.getHost().name),
                ()->Assertions.assertEquals(1,m.getSize()),
                ()->Assertions.assertEquals(0,m.getTime()),
                ()->Assertions.assertEquals(10,m.getNumberQuestions())
        );
    }

    @Test
    public void matchConstructor3(){
        Match m = new Match("tournament","Test", new Player("test"),4);
        Assertions.assertAll(
                ()->Assertions.assertNotNull(m.getPlayers()),
                ()->Assertions.assertEquals(0,m.getPlayers().size()),
                ()->Assertions.assertFalse(m.isAvailable()),
                ()->Assertions.assertEquals("tournament",m.getType()),
                ()->Assertions.assertEquals("test",m.getHost().name),
                ()->Assertions.assertEquals(4,m.getSize()),
                ()->Assertions.assertEquals(0,m.getTime()),
                ()->Assertions.assertEquals(10,m.getNumberQuestions())
        );
    }

    @Test
    public void matchConstructor4(){
        Match m = new Match("tournament","Test", new Player("test"),4,1,15);
        Assertions.assertAll(
                ()->Assertions.assertNotNull(m.getPlayers()),
                ()->Assertions.assertEquals(0,m.getPlayers().size()),
                ()->Assertions.assertFalse(m.isAvailable()),
                ()->Assertions.assertEquals("tournament",m.getType()),
                ()->Assertions.assertEquals("test",m.getHost().name),
                ()->Assertions.assertEquals(4,m.getSize()),
                ()->Assertions.assertEquals(1,m.getTime()),
                ()->Assertions.assertEquals(15,m.getNumberQuestions())
        );
    }

    @Test
    public void matchID(){
        ArrayList<Match> matches = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            matches.add(new Match());
        }
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < matches.size(); j++){
                int cont = 0;
                for(Match m : matches){
                    if(m.equals(matches.get(j)))
                        cont++;
                }
                Assertions.assertEquals(1,cont);
            }
        }
    }

    @Test
    public void testSetters(){
        Match m = new Match("friendly",new Player("name"));
        m.setTime(20);
        m.setPlayers(new ArrayList<>());
        m.setHost(new Player("name2"));
        m.setName("Match");
        m.setAvailable(true);
        m.setType("tournament");
        Assertions.assertAll(
                () -> Assertions.assertEquals(20,m.getTime()),
                () -> Assertions.assertEquals(0, m.getPlayers().size()),
                () -> Assertions.assertEquals("name2",m.getHost().name),
                () -> Assertions.assertEquals("Match",m.getName()),
                () -> Assertions.assertEquals("tournament",m.getType()),
                () -> Assertions.assertTrue(m.isAvailable())
        );
    }

    @Test
    public void togglePlayers(){
        Match m = new Match("friendly",new Player("name"));
        Assertions.assertEquals(0,m.getPlayers().size());
        m.addPlayer(new Player("name"));
        m.addPlayer(new Player("name2"));
        m.addPlayer(new Player("name3"));
        Assertions.assertEquals(1,m.getPlayers().size());
        Match m2 = new Match("friendly","test",new Player("name"),4);
        System.out.println(m2);
        m2.addPlayer(new Player("name"));
        m2.addPlayer(new Player("name2"));
        m2.addPlayer(new Player("name3"));
        Assertions.assertEquals(3,m2.getPlayers().size());
        Player pp  = m2.getPlayer("name2");
        Assertions.assertEquals("name2",pp.name);
        Assertions.assertNull(m2.getPlayer("name4"));
        Assertions.assertNull(m2.getPlayer(new Player("name5")));

    }
}
