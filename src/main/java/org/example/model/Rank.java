package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Rank implements Comparable<Rank>, Serializable {
    public float points;
    public String name;
    public Rank(String name){
        this.name = name;
        this.points = 0;
    }
    public Rank(String name, int points){
        this.name = name;
        this.points = points;
    }

    public void addOne(){
        this.points = this.points + 1;
    }

    public void addHalf(){
        this.points = this.points + 0.5f;
    }

    public void set(float points){
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return name.equals(rank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Rank o) {
        if(this.points > o.points)
            return 1;
        if(this.points == o.points)
            return 0;
        return -1;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "points=" + points +
                ", name='" + name + '\'' +
                '}';
    }
}
