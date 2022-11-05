package org.example.view;

import org.example.controller.FriendlyModeController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FriendlyModeView {
    private JPanel panel;
    public FriendlyModeView(JFrame frame, String name, ArrayList<Match> matches, Sender sender, MatchChecker mm){
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(25,25,25,25));
        panel.setLayout(new GridLayout(7,1));

        JLabel title = new JLabel("Friendly mode");
        Font font = title.getFont();
        title.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(title);

        JLabel description = new JLabel("Select a match or create a new one.");
        panel.add(description);

        JScrollPane scrollable = new JScrollPane();
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JPanel content = new JPanel();
        content.setLayout(new GridLayout((matches.size()),1));
        for(Match m : matches){
            JPanel content2 = new JPanel();
            content2.setLayout(new GridLayout(5,1));
            JLabel l1 = new JLabel("Match: "+m.name);
            content2.add(l1);
            JLabel l2 = new JLabel("Host: "+m.host);
            content2.add(l2);
            JLabel l3 = new JLabel("Size: "+m.players.size());
            content2.add(l3);
            JButton enter = new JButton("Enter");
            enter.addActionListener(e -> {
                System.out.println("Button clicked of match: "+m.name);
            });
            content2.add(enter);
            JSeparator js = new JSeparator();
            content2.add(js);
            content.add(content2);
            content2.setVisible(true);
        }
        content.setVisible(true);
        scrollable.setViewportView(content);

        panel.add(scrollable);
        JLabel or = new JLabel("or");
        panel.add(or);
        JButton button = new JButton("Create a new match");
        panel.add(button);
        JSeparator sp = new JSeparator();
        panel.add(sp);
        JButton back = new JButton("Go Back");
        panel.add(back);

        FriendlyModeController fmc = new FriendlyModeController(frame, panel, matches, button, back, content, name, sender, mm);

        content.setVisible(true);
        panel.setVisible(true);
    }

    public JPanel getPanel(){
        return this.panel;
    }
}
