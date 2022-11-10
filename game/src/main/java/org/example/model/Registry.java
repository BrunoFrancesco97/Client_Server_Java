package org.example.model;

import org.example.interfaces.Registries;

public class Registry<K> implements Registries<K> {
    private K element;
    @Override
    public void set(K el) {
        this.element = element;
    }

    @Override
    public K get() {
        return this.element;
    }

    @Override
    public void clear() {
        this.element = null;
    }
}
