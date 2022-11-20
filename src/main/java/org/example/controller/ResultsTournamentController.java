package org.example.controller;

import org.example.model.MatchChecker;
import org.example.utils.Sender;
import org.example.view.ModeView;

import javax.swing.*;

public class ResultsTournamentController{
    public ResultsTournamentController(JFrame frame, JPanel results, JButton jb, String name, Sender sender, MatchChecker mm){
        jb.addActionListener(e -> {
            frame.remove(results);
            frame.add(new ModeView(frame,name,sender, mm).getPanel());
            frame.validate();
        });
    }
}

