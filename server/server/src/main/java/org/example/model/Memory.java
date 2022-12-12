package org.example.model;

import java.util.ArrayList;

public class Memory<K> {
    private ArrayList<K> memory;
    public Memory(){
        this.memory = new ArrayList<>();
    }
    public synchronized void add(K el){
        if(el != null)
            this.memory.add(el);
    }
    public synchronized void remove(K el){
        this.memory.remove(el);
    }
    public synchronized void remove(int index){
        this.memory.remove(index);
    }
    public synchronized boolean checkElement(K el){
        return this.memory.contains(el);
    }

    public synchronized ArrayList<K> getMemory() {
        return memory;
    }

    public synchronized K get(K element){
        int index = this.memory.indexOf(element);
        if(index != -1)
            return this.memory.get(index);
        return null;
    }
    public synchronized int size(){
        return this.memory.size();
    }
    public synchronized void printContent(){
        for(K el : memory){
            System.out.println(el);
        }
    }
    public synchronized ArrayList<K> clone(){
        ArrayList<K> arr = new ArrayList<>();
        arr.addAll(this.memory);
        return arr;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memory=" + memory +
                '}';
    }
}
