package org.example.controller;

import org.example.model.MatchChecker;
import org.example.utils.Sender;
import org.example.view.ModeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultsController {
    public ResultsController(JFrame frame, JPanel results, JButton jb, String name, Sender sender, MatchChecker mm){
        jb.addActionListener(e -> {
            frame.remove(results);
            frame.add(new ModeView(frame,name,sender, mm).getPanel());
            frame.validate();
        });
        /*jb.addActionListener(e -> {

        });*/
    }
}
