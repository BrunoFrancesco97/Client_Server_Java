


import org.example.model.Memory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class MemoryTest {
    @Test
    public void memoryAdd1(){
        Memory<String> mem = new Memory<>();
        Assertions.assertTrue(mem.size() == 0);
        mem.add("Test1");
        mem.add("Test2");
        Assertions.assertTrue(mem.size() == 2);
    }
    @Test
    public void memoryAdd2(){
        Memory<String> mem = new Memory<>();
        Assertions.assertTrue(mem.size() == 0);
        mem.add(null);
        mem.add(null);
        Assertions.assertTrue(mem.size() == 0);
    }
    @Test
    public void memoryDelete1(){
        Memory<String> mem = new Memory<>();
        mem.add("Test1");
        mem.add("Test2");
        mem.remove("Test1");
        Assertions.assertTrue(mem.size() == 1);

    }
    @Test
    public void memoryDelete2(){
        Memory<String> mem = new Memory<>();
        mem.add("Test1");
        mem.add("Test2");
        mem.remove(1);
        Assertions.assertTrue(mem.size() == 1);

    }

    @Test
    public void memoryGet(){
        Memory<String> mem = new Memory<>();
        mem.add("Test1");
        mem.add("Test2");
        String get = mem.get("Test1");
        Assertions.assertTrue(get.equals("Test1"));
    }

    @Test
    public void memoryCheck(){
        Memory<String> mem = new Memory<>();
        mem.add("Test1");
        mem.add("Test2");
        boolean get = mem.checkElement("Test1");
        Assertions.assertEquals(true, get);
    }

    @Test
    public void memoryClone(){
        Memory<String> mem = new Memory<>();
        mem.add("Test1");
        mem.add("Test2");
        ArrayList<String> get = mem.clone();
        Assertions.assertEquals(true, mem.getMemory().equals(get));
    }
}
