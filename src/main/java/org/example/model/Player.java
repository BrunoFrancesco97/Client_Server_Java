package org.example.model;

import org.example.utils.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements Serializable {

    public String name;
    public ArrayList<Question> questions;
    public Score score;
    private int id;
    public Player(){
        this.name = null;
        this.questions = new ArrayList<>();
        this.score = null;
        this.id = Utility.randomIDGenerator(10000);
    }
    public Player(String name){
        this.name = name;
        this.questions = new ArrayList<>();
        this.score = new Score(this.name);
        this.id = Utility.randomIDGenerator(10000);
    }
    public void setName(String name){
        this.name = name;
        this.score = new Score(this.name);
    }

    public void clearQuestions(){
        questions.clear();
    }
    public void addQuestion(Question q){
        questions.add(q);
    }

    public Question pickQuestion(){
        int length = questions.size()-1;
        int random_int = (int)Math.floor(Math.random()*(length+1));
        Question q = questions.get(random_int);
        questions.remove(random_int);
        return q;
    }

    public boolean hasQuestion(){
        return (this.questions.size() > 0);
    }

    public int getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
