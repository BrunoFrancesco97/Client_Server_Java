package org.example.controller;

import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;
import org.example.view.MatchCreatorView;
import org.example.view.ModeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendlyModeController {

    public FriendlyModeController(JFrame frame, JPanel panel, ArrayList<Match> matches, JButton create, JButton back, String name, Sender sender, MatchChecker mm){
        create.addActionListener(e -> {
            frame.remove(panel);
            frame.add(new MatchCreatorView(frame, name, matches, sender, mm).getPanel());
            frame.validate();
        });
        back.addActionListener(e -> {
            frame.remove(panel);
            frame.add(new ModeView(frame, name, sender, mm).getPanel());
            frame.validate();
        });
    }
}
