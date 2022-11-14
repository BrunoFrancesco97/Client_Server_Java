package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;
import org.example.view.QuestionView;
import org.example.view.ResultsView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionController {
    public QuestionController(JFrame frame, JPanel question, JButton jb, JTextField answer, Question q, String name, Sender sender, MatchChecker mm, boolean lecit, Timer tQuiz, AtomicInteger seconds){
        if(!lecit){
            TimerTask task = new TimerTask() {
                public int i = 0;
                @Override
                public void run() {
                    i++;
                    seconds.getAndAdd(1);
                    System.out.println("Seconds passed: "+i);
                    if(i > 60){
                        tQuiz.cancel(); //Delete timer if test is taking more than n/60 minutes
                        System.out.println("Timer ended!");
                        String text = answer.getText();
                        q.checkAnswer(text);
                        Message response = sender.sendAndRead(new Message<>(name, "END_TIMER",q));
                        if(response != null && response.getEvent().equals("END_TIMER") && response.getMessage() != null){
                            printScoresFriendly(frame, question, response, name, sender, mm);
                        }
                        //TODO: Ho un dubbio, mm sotto lo setto solo su practice e non su friendly, controllare se ci sono bug
                    }
                }
            };
            tQuiz.schedule(task, 1500, 1000);
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
                        frame.add(new QuestionView(frame,name,newQuestion,sender, mm, true, tQuiz, seconds).getPanel());
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
