package org.example.view;

import org.example.controller.FriendlyModeController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class FriendlyModeView {
    private JPanel panel;
    public FriendlyModeView(JFrame frame, String name, ArrayList<Match> matches, Sender sender, MatchChecker mm){
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(25,25,25,25));
        panel.setLayout(new GridLayout(8,1));

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
        content.setVisible(true);
        scrollable.setViewportView(content);

        panel.add(scrollable);
        JButton reload = new JButton("Reload");
        panel.add(reload);
        JLabel or = new JLabel("or");
        panel.add(or);
        JButton button = new JButton("Create a new match");
        panel.add(button);
        JSeparator sp = new JSeparator();
        panel.add(sp);
        JButton back = new JButton("Go Back");
        panel.add(back);

        FriendlyModeController fmc = new FriendlyModeController(frame, panel, matches, button, back, reload, content, name, sender, mm);

        content.setVisible(true);
        panel.setVisible(true);
    }

    public JPanel getPanel(){
        return this.panel;
    }
}
