package org.example.model;

import java.io.Serializable;

public class MatchChecker implements Serializable {
    private String name; //Name of who was doing the match

    private boolean isGoingOn;
    private String type;
    private Question question;
    private String match; //Name of the match

    private int position;
    public MatchChecker(){
        this.isGoingOn = false;
        this.question = null;
        this.type = null;
        this.match = null;
        this.name = null;
        this.position = 1;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGoingOn() {
        return isGoingOn;
    }

    public void setGoingOn(boolean goingOn) {
        isGoingOn = goingOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "MatchChecker{" +
                "name='" + name + '\'' +
                ", isGoingOn=" + isGoingOn +
                ", type='" + type + '\'' +
                ", question=" + question +
                ", match='" + match + '\'' +
                ", position=" + position +
                '}';
    }
}
