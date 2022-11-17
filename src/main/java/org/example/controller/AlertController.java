package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.utils.Sender;
import org.example.view.ModeView;
import org.example.view.QuestionView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertController {
    public AlertController(JFrame frame, JPanel alert, String name, String type, JButton buttonYes, JButton buttonNo, Sender sender, MatchChecker mm){
        buttonYes.addActionListener(e -> {
            Message response = sender.sendAndRead(new Message(name, "RESUME",type));
            if(response != null && response.getMessage() != null && response.getEvent().equals("GAME")){
                mm.setType("practice");
                mm.setGoingOn(true);
                mm.setQuestion((Question) response.getMessage());
                System.out.println(mm);
                frame.remove(alert);
                frame.add(new QuestionView(frame,name, (Question) response.getMessage(),sender, mm, true, null, null, null, 0).getPanel());
                frame.validate();
            }
        });
        buttonNo.addActionListener(e -> {
            sender.send(new Message(name, "REMOVE"));
            frame.remove(alert);
            frame.add(new ModeView(frame,name,sender, mm).getPanel());
            frame.validate();
        });
    }
}
