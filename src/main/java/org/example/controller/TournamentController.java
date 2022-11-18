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

//TODO: TIMER MAGARI ANCHE VISIVO SU QUANTO TEMPO IL MATCH DURA PER IL SINGOLO GIOCATORE
//TODO: POTREI FARE TIMER CHE PARTE DA ZERO PER OGNI QUESTION E MISURA QUANTO TEMPO CI METTE A RISPONDERE AD OGNI DOMANDA
//TODO: AL TERMINE VIENE FATTA LA SOMMA ED A PARITA' DI PUNTI VIENE DECRETATO IL MIGLIORE

public class TournamentController {
    public TournamentController(JFrame frame, JPanel question, JButton jb, ArrayList<JRadioButton> list, Question q, String name, Sender sender, MatchChecker mm, JLabel timeLabel, int questions, int iterator){
        jb.addActionListener(e -> {
            String value = null;
            for(JRadioButton el : list){
                if(el.isSelected()){
                    value = el.getText();
                    break;
                }
            }
            q.checkAnswer(value);
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
        });
    }

}
