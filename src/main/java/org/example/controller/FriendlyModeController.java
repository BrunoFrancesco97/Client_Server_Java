package org.example.controller;

import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;
import org.example.view.MatchCreatorView;
import org.example.view.ModeView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FriendlyModeController {

    public FriendlyModeController(JFrame frame, JPanel panel, ArrayList<Match> matches, JButton create, JButton back, JPanel content, String name, Sender sender, MatchChecker mm){
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Running");
                Message responsef = sender.send(new Message<>(name, "START","friendly"));
                ArrayList<Match> newMatches = (ArrayList<Match>) responsef.getMessage();
                content.removeAll();
                //JPanel content = new JPanel();
                content.setLayout(new GridLayout((newMatches.size()),1));
                for(Match m : newMatches){
                    JPanel content2 = new JPanel();
                    content2.setLayout(new GridLayout(4,1));
                    JLabel l1 = new JLabel("Match: "+m.name);
                    content2.add(l1);
                    JLabel l2 = new JLabel("Host: "+m.host);
                    content2.add(l2);
                    JLabel l3 = new JLabel("Size: "+m.players.size());
                    content2.add(l3);
                    JSeparator js = new JSeparator();
                    content2.add(js);
                    content.add(content2);
                    content2.setVisible(true);
                }
                content.setVisible(true);
                frame.revalidate();
                frame.repaint();
            };
        };
        t.scheduleAtFixedRate(tt,2000,2000);

        create.addActionListener(e -> {
            frame.remove(panel);
            frame.add(new MatchCreatorView(frame, name, matches, sender, mm,t).getPanel());
            frame.validate();
        });
        back.addActionListener(e -> {
            frame.remove(panel);
            frame.add(new ModeView(frame, name, sender, mm, t).getPanel());
            frame.validate();
        });
    }
}
