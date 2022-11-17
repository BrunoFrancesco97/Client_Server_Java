package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;
import org.example.view.QuestionView;
import org.example.view.ResultsView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionController {
    public QuestionController(JFrame frame, JPanel question, JButton jb, JTextField answer, Question q, String name, Sender sender, MatchChecker mm, boolean lecit, Timer tQuiz, AtomicInteger seconds, JLabel label, int time, int questions, int iterator){
        if(!lecit){
            TimerTask task = new TimerTask() {
                public int i = seconds.get();
                @Override
                public void run() {
                    i++;
                    int minutes = i / 60;
                    int secondss = i % 60;
                    seconds.getAndAdd(1);
                    if(secondss < 10)
                        label.setText("Time passed in minutes: "+minutes+":"+"0"+secondss+"/"+time+":00");
                    else  label.setText("Time passed in minutes: "+minutes+":"+secondss+"/"+time+":00");
                    if(i > time * 60){
                        tQuiz.cancel(); //Delete timer if test is taking more than n/60 minutes
                        Message response = sender.sendAndRead(new Message<>(name, "END_TIMER"));
                        if(response != null && response.getEvent().equals("END_TIMER") && response.getMessage() != null){
                            printScoresFriendly(frame, question, response, name, sender, mm);
                        }
                    }
                }
            };
            tQuiz.schedule(task, 500, 1000);
        }
        jb.addActionListener(e -> {
            if(answer.getText() != null && answer.getText().length() > 0){ //TODO: DOVREI TOGLIERE TUTTI GLI SPAZI VUOTI E CONFRONTARE LA STRINGA SOLO A QUEL PUNTO
                String text = answer.getText();
                q.checkAnswer(text);
                Message response = sender.sendAndRead(new Message<>(name, "GAME",q));
                switch (response.getEvent().toLowerCase()){
                    case "game":
                        frame.remove(question);
                        Question newQuestion = (Question) response.getMessage();
                        frame.add(new QuestionView(frame,name,newQuestion,sender, mm, true, tQuiz, seconds, label, time, questions,iterator+1).getPanel());
                        frame.validate();
                        break;
                    case "end":
                        switch (mm.getType()){
                            case "practice":
                                mm.setGoingOn(false);
                                mm.setType(null);
                                mm.setMatch(null);
                                frame.remove(question);
                                Score score = (Score) response.getMessage();
                                frame.add(new ResultsView(frame,name,score,sender, mm,false).getPanel());
                                frame.validate();
                                break;
                            case "friendly":
                                if(tQuiz != null)
                                    tQuiz.cancel();
                                printScoresFriendly(frame, question, response, name, sender, mm);
                                break;
                        }
                }
            }
        });


    }

    private void printScoresFriendly(JFrame frame, JPanel question, Message response, String name, Sender sender, MatchChecker mm){
        frame.remove(question);
        Score score2 = (Score) response.getMessage();
        JPanel waiting = new ResultsView(frame,name,score2,sender, mm,true).getPanel();
        frame.add(waiting);
        frame.validate();
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Message check = sender.sendAndRead(new Message<>(name, "IS_END", mm.getMatch()));
                if(check != null && check.getMessage() != null){
                    if(check.getMessage() instanceof ArrayList<?>){
                        t.cancel();
                        mm.setGoingOn(false);
                        mm.setType(null);
                        mm.setMatch(null);
                        frame.remove(waiting);
                        frame.add(new ResultsView(frame,name,(ArrayList<Score>) check.getMessage(),sender, mm,false).getPanel());
                        frame.validate();
                    }
                }
            }
        };
        t.scheduleAtFixedRate(tt,1000,1000);
    }
}
