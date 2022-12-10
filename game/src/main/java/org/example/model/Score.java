package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable {
    public ArrayList<Question> questions;
    public String name;
    public boolean completed;
    public Score(String name){
        this.name = name;
        questions = new ArrayList<>();
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getNumberOfCorrectAnswers(){
        int i = 0;
        for(Question q : questions){
            if(q.correct)
                i++;
        }
        return i;
    }
    public int getNumberOfUnCorrectAnswers(){
        int i = 0;
        for(Question q : questions){
            if(!q.correct)
                i++;
        }
        return i;
    }
    public void merge(Score s){
        this.questions.addAll(s.questions);
    }
    public void addQuestion(Question q){
        if(q != null)
            this.questions.add(q);
    }

    public boolean containQuestion(Question q){
        for(Question qq : this.questions){
            if(qq.equals(q))
                return true;
        }
        return false;
    }

    public Question getQuestion(int i){
        if(i >= 0 && i < questions.size())
            return this.questions.get(i);
        return null;
    }
    @Override
    public String toString() {
        return "Score{" +
                "questions=" + questions +
                ", name='" + name + '\'' +
                '}';
    }
}
