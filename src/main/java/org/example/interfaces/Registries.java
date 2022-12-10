package org.example.interfaces;

public interface Registries<K>{
    public void set(K el);
    public K get();
    public void clear();
}
