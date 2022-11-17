package org.example.model;

import java.io.Serializable;

public class Question implements Serializable {
    public String quest, answer, given;
    public boolean correct;
    public Question(String quest, String answer){
        this.quest = quest;
        this.answer = answer;
        this.correct = false;
        this.given = null;
    }

    public final boolean checkAnswer(String answer){
        String newString = answer.toLowerCase().replaceAll("\\s+","");
        if(newString.equals(this.answer.toLowerCase())){
            this.correct = true;
        }
        this.given = answer;
        return this.correct;
    }

    @Override
    public String toString() {
        return this.quest;
    }
}
