package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable {
    public ArrayList<Question> questions;
    public String name;
    public Score(String name){
        this.name = name;
        questions = new ArrayList<>();
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
    @Override
    public String toString() {
        String answer = "**REPORT**\nName: "+this.name;
        for(Question q : questions){
            answer = answer+"\n"+q.quest+"\nGiven: "+q.given+"\nExpected: "+q.answer+"\nIs correct?: "+q.correct+"\n******";
        }
        return answer;
    }
}
