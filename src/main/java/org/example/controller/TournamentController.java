package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.utils.Sender;
import org.example.view.TournamentView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

//TODO: TIMER MAGARI ANCHE VISIVO SU QUANTO TEMPO IL MATCH DURA PER IL SINGOLO GIOCATORE
//TODO: POTREI FARE TIMER CHE PARTE DA ZERO PER OGNI QUESTION E MISURA QUANTO TEMPO CI METTE A RISPONDERE AD OGNI DOMANDA
//TODO: AL TERMINE VIENE FATTA LA SOMMA ED A PARITA' DI PUNTI VIENE DECRETATO IL MIGLIORE

public class TournamentController {
    private final int DEFAULT_QUESTION_TIME = 30;
    public TournamentController(JFrame frame, JPanel question, JButton jb, ArrayList<JRadioButton> list, Question q, String name, Sender sender, MatchChecker mm, JLabel timeLabel, int questions, int iterator, boolean casistic){
        if(!casistic){
            Timer t = new Timer();
            AtomicInteger seconds = new AtomicInteger(DEFAULT_QUESTION_TIME);
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    int got = seconds.decrementAndGet();
                    if(got < 10) timeLabel.setText("Time remained for this question: 00:0"+got);
                    else timeLabel.setText("Time remained for this question: 00:"+got);
                    if(got == 0){ //30 seconds is the max time a question can take by default
                        t.cancel();
                        frame.remove(question);
                        frame.add(new TournamentView(frame, name, q, sender, mm, null,questions, iterator,true).getPanel());
                        frame.validate();
                    }
                }
            };
            t.scheduleAtFixedRate(tt,10,1000);

            jb.addActionListener(e -> {
                t.cancel();
                String value = null;
                for(JRadioButton el : list){
                    if(el.isSelected()){
                        value = el.getText();
                        break;
                    }
                }
                if(value != null){
                    q.checkAnswer(value);
                    q.setSeconds(DEFAULT_QUESTION_TIME-seconds.get());
                    Timer t2 = new Timer();
                    TimerTask tt2 = new TimerTask() {
                        @Override
                        public void run() {
                            Message response1 = sender.sendAndRead(new Message<>(name, "UPDATE_NEXT",q));
                            if(response1.getMessage().equals("no")){
                                t2.cancel();
                                frame.remove(question);
                                frame.add(new TournamentView(frame, name, q, sender, mm, null,questions, iterator,true).getPanel());
                                frame.validate();
                            }else{
                                t2.cancel();
                                Message response2 = sender.sendAndRead(new Message<>(name, "GAME",q));
                                Question newQuestion = (Question) response2.getMessage();
                                switch (response2.getEvent().toLowerCase()){
                                    case "game":
                                        t2.cancel();
                                        frame.remove(question);
                                        frame.add(new TournamentView(frame, name, newQuestion, sender, mm, timeLabel,questions, iterator+1,false).getPanel());
                                        frame.validate();
                                        break;
                                    case "end":
                                        break;
                                }
                            }
                        }
                    };
                    t2.scheduleAtFixedRate(tt2,10,1000);
                }
            });
        }else{
            System.out.println("Entered here");
            Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    Message response1 = sender.sendAndRead(new Message<>(name, "UPDATE_NEXT",q));
                    if(response1.getMessage().equals("ok")){
                        t.cancel();
                        Message response2 = sender.sendAndRead(new Message<>(name, "GAME",q));
                        Question newQuestion = (Question) response2.getMessage();
                        switch (response2.getEvent().toLowerCase()){
                            case "game":
                                t.cancel();
                                frame.remove(question);
                                frame.add(new TournamentView(frame, name, newQuestion, sender, mm, new JLabel(),questions, iterator+1,false).getPanel());
                                frame.validate();
                                break;
                            case "end":
                                break;
                        }
                    }
                }
            };
            t.scheduleAtFixedRate(tt,10,1000);
        }
    }

}
