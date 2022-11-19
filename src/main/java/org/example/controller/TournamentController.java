package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;
import org.example.view.ResultsView;
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
    public TournamentController(JFrame frame, JPanel question, JButton jb, ArrayList<JRadioButton> list, Question q, String name, Sender sender, MatchChecker mm, JLabel timeLabel, int questions, int iterator){
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
                    Message response = sender.sendAndRead(new Message<>(name, "GAME",q));
                    switch (response.getEvent().toLowerCase()){
                        case "game":
                            frame.remove(question);
                            Question newQuestion = (Question) response.getMessage();
                            frame.add(new TournamentView(frame, name, newQuestion, sender, mm, timeLabel,questions, iterator+1,false).getPanel());
                            frame.validate();
                            break;
                        case "end":
                            //printScoresFriendly(frame, question, response, name, sender, mm);
                            break;
                    }
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
            q.checkAnswer(value);
            q.setSeconds(DEFAULT_QUESTION_TIME-seconds.get());
            Timer t2 = new Timer();
            TimerTask tt2 = new TimerTask() {
                @Override
                public void run() {
                    Message response1 = sender.sendAndRead(new Message<>(name, "UPDATE_NEXT",q));
                    Message response2 = sender.sendAndRead(new Message<>(name, "GAME",q));
                    switch (response2.getEvent().toLowerCase()){
                        case "game":
                            if(response1.getMessage().equals("no")){
                                frame.remove(question);
                                frame.add(new TournamentView(frame, name, null, sender, mm, null,questions, iterator,true).getPanel());
                                frame.validate();
                            }else{
                                t2.cancel();
                                frame.remove(question);
                                Question newQuestion = (Question) response2.getMessage();
                                frame.add(new TournamentView(frame, name, newQuestion, sender, mm, timeLabel,questions, iterator+1,false).getPanel());
                                frame.validate();
                            }
                            break;
                        case "end":
                            //printScoresFriendly(frame, question, response, name, sender, mm);
                            break;
                    }
                }
            };
            t2.scheduleAtFixedRate(tt2,10,1000);
        });
    }

}
