package org.example.view;

import org.example.controller.MatchCreatorController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class MatchCreatorView {
    private JPanel panel;
    public MatchCreatorView(JFrame frame, String name, ArrayList<Match> matches, Sender sender, MatchChecker mm, Timer t){
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(25,25,25,25));
        panel.setLayout(new GridLayout(5,1));
        JLabel title = new JLabel("Create a new match");
        Font font = title.getFont();
        title.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(title);
        JLabel description = new JLabel("Name of the match: ");
        panel.add(description);
        JTextField nameMatch = new JTextField(20);
        panel.add(nameMatch);
        JButton create = new JButton("Create");
        panel.add(create);

        JButton back = new JButton("Go back");
        panel.add(back);

        MatchCreatorController mc = new MatchCreatorController(frame,panel,nameMatch, back, create, name,matches, sender, mm, t);

        panel.setVisible(true);

    }
    public JPanel getPanel(){
        return this.panel;
    }
}
