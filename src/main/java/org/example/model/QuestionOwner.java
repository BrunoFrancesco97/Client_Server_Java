package org.example.model;

public class QuestionOwner {
    private Question quest;
    private String player;

    public QuestionOwner(Question quest, String p){
        this.quest = quest;
        this.player = p;
    }

    public Question getQuest() {
        return quest;
    }

    public void setQuest(Question quest) {
        this.quest = quest;
    }

    public String getP() {
        return player;
    }

    public void setP(String p) {
        this.player = p;
    }

    @Override
    public String toString() {
        return "QuestionOwner{" +
                "quest=" + quest +
                ", player='" + player + '\'' +
                '}';
    }
}
