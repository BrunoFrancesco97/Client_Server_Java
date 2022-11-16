package org.example.model;

public class MemoryModifier {
    private static final MemoryModifier modifier = new MemoryModifier();
    private MemoryModifier(){};

    public static MemoryModifier getModifier(){
        return modifier;
    }

}
