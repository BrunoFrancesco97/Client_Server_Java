package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;
import org.example.view.QuestionView;
import org.example.view.ResultsView;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionController {
    public QuestionController(JFrame frame, JPanel question, JButton jb, JTextField answer, Question q, String name, Sender sender, MatchChecker mm){
        jb.addActionListener(e -> {
            if(answer.getText() != null && answer.getText().length() > 0){ //TODO: DOVREI TOGLIERE TUTTI GLI SPAZI VUOTI E CONFRONTARE LA STRINGA SOLO A QUEL PUNTO
                String text = answer.getText();
                q.checkAnswer(text);
                Message response = sender.sendAndRead(new Message<>(name, "GAME",q));
                switch (response.getEvent().toLowerCase()){
                    case "game":
                        System.out.println(mm);
                        frame.remove(question);
                        Question newQuestion = (Question) response.getMessage();
                        frame.add(new QuestionView(frame,name,newQuestion,sender, mm).getPanel());
                        frame.validate();
                        break;
                    case "end":
                        System.out.println(mm);
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
                                System.out.println("Sono qui");
                                //mm.setGoingOn(false);
                                //mm.setType(null);
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
                                        if(check != null && check.getMessage() != null && check.getMessage() instanceof String){
                                            if(check.getMessage().equals("Y")){
                                                t.cancel();
                                                mm.setGoingOn(false);
                                                mm.setType(null);
                                                mm.setMatch(null);
                                                frame.remove(waiting);
                                                frame.add(new ResultsView(frame,name,score2,sender, mm,false).getPanel());
                                                frame.validate();
                                            }
                                        }
                                    }
                                };
                                t.scheduleAtFixedRate(tt,1000,1000);
                                //TODO: DOVREI CONTROLLARE CHE TUTTI ABBIANO TERMINATO, QUINDI FACCIO UN POLLING, SE HANNO TERMINATO
                                //TODO: MOSTRO LA VIEW DEI RISULTATI E SETTO MM.SETGOINGON A FALSE E SETTYPE A NULL

                                break;
                        }
                }
            }
        });
    }
}
