import org.example.model.Rank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RankTest {

    @Test
    public void test(){
        Rank r1 = new Rank("fra");
        Rank r2 = new Rank("bru",10);
        Assertions.assertAll(
                ()->Assertions.assertEquals(0, r1.points),
                ()->Assertions.assertEquals(10, r2.points),
                ()->Assertions.assertEquals("fra",r1.name),
                ()->Assertions.assertEquals("bru",r2.name)
        );
        r1.addHalf();
        Assertions.assertEquals(0.5f, r1.points);
        r1.addOne();
        Assertions.assertEquals(1.5f, r1.points);
        r1.set(4);
        Assertions.assertEquals(4, r1.points);
        Assertions.assertEquals(1, r2.compareTo(r1));
        Assertions.assertNotEquals(0, r2.compareTo(r1));
        Assertions.assertNotEquals(-1, r2.compareTo(r1));
        Assertions.assertEquals(-1, r1.compareTo(r2));
        Assertions.assertNotEquals(0, r1.compareTo(r2));
        Assertions.assertNotEquals(1, r1.compareTo(r2));
    }
}
