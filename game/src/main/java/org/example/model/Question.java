package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Question implements Serializable {
    public String quest, answer, given,wrong1,wrong2,wrong3,wrong4;
    public boolean correct;
    public int seconds; //Seconds passed in order to give the answer
    public Question(String quest, String answer){
        this.quest = quest;
        this.answer = answer;
        this.correct = false;
        this.given = null;
        this.wrong1 = null;
        this.wrong2 = null;
        this.wrong3 = null;
        this.wrong4 = null;
    }

    public Question(String quest, String answer, String wrong1, String wrong2, String wrong3, String wrong4){
        this.quest = quest;
        this.answer = answer;
        this.correct = false;
        this.given = null;
        this.wrong1 = wrong1;
        this.wrong2 = wrong2;
        this.wrong3 = wrong3;
        this.wrong4 = wrong4;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public final boolean checkAnswer(String answer){
        String newString = answer.toLowerCase().replaceAll("\\s+","");
        if(newString.equals(this.answer.toLowerCase())){
            this.correct = true;
        }else{
            this.correct = false;
        }
        this.given = answer;
        return this.correct;
    }

    @Override
    public String toString() {
        return "Question{" +
                "quest='" + quest + '\'' +
                ", answer='" + answer + '\'' +
                ", given='" + given + '\'' +
                ", wrong1='" + wrong1 + '\'' +
                ", wrong2='" + wrong2 + '\'' +
                ", wrong3='" + wrong3 + '\'' +
                ", wrong4='" + wrong4 + '\'' +
                ", correct=" + correct +
                '}';
    }

    public ArrayList<String> getAllAnswers(){
        ArrayList<String> answers = new ArrayList<>();
        answers.add(answer);
        if(wrong1 != null && wrong2 != null && wrong3 != null && wrong4 != null){
            answers.add(wrong1);
            answers.add(wrong2);
            answers.add(wrong3);
            answers.add(wrong4);
        }
        Collections.shuffle(answers);
        return answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return this.quest.equals((question.quest));
    }

    @Override
    public int hashCode() {
        return Objects.hash(quest, answer, given, wrong1, wrong2, wrong3, wrong4, correct, seconds);
    }
}
