package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;
import org.example.view.QuestionView;
import org.example.view.ResultsView;

import javax.swing.*;

public class QuestionController {
    public QuestionController(JFrame frame, JPanel question, JButton jb, JTextField answer, Question q, String name, Sender sender, MatchChecker mm){
        jb.addActionListener(e -> {
            if(answer.getText() != null && answer.getText().length() > 0){ //TODO: DOVREI TOGLIERE TUTTI GLI SPAZI VUOTI E CONFRONTARE LA STRINGA SOLO A QUEL PUNTO
                String text = answer.getText();
                q.checkAnswer(text);
                Message response = sender.send(new Message<>(name, "GAME",q));
                switch (response.getEvent().toLowerCase()){
                    case "game":
                        frame.remove(question);
                        Question newQuestion = (Question) response.getMessage();
                        frame.add(new QuestionView(frame,name,newQuestion,sender, mm).getPanel());
                        frame.validate();
                        break;
                    case "end":
                        mm.setGoingOn(false);
                        frame.remove(question);
                        Score score = (Score) response.getMessage();
                        frame.add(new ResultsView(frame,name,score,sender, mm).getPanel());
                        frame.validate();
                        break;
                }
            }
        });
    }
}
