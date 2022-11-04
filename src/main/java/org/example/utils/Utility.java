package org.example.utils;

public abstract class Utility {
    public static int randomIDGenerator(int max){
        return ((int)Math.floor(Math.random()*(max+1)));
    }

}
