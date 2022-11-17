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
    public MatchCreatorView(JFrame frame, String name, ArrayList<Match> matches, Sender sender, MatchChecker mm){
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(25,25,25,25));
        panel.setLayout(new GridLayout(12,1));
        JLabel title = new JLabel("Create a new match");
        Font font = title.getFont();
        title.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(title);
        JLabel description = new JLabel("Name of the match: ");
        panel.add(description);
        JTextField nameMatch = new JTextField(20);
        panel.add(nameMatch);
        JLabel description2 = new JLabel("Maximum number of people allowed:  ");
        panel.add(description2);
        SpinnerModel people = new SpinnerNumberModel(4,1,9,1);
        JSpinner maxSize = new JSpinner(people);
        panel.add(maxSize);
        JLabel description3 = new JLabel("Maximum duration of the match in minutes:");
        panel.add(description3);
        SpinnerModel time = new SpinnerNumberModel(2,2,10,2);
        JSpinner timeMatch = new JSpinner(time);
        panel.add(timeMatch);
        JLabel description4 = new JLabel("Numbers of questions of the match:");
        panel.add(description4);
        SpinnerModel questions = new SpinnerNumberModel(5,5,15,1);
        JSpinner questionsSize = new JSpinner(questions);
        panel.add(questionsSize);
        JSeparator sp = new JSeparator();
        panel.add(sp);
        JButton create = new JButton("Create");
        panel.add(create);

        JButton back = new JButton("Go back");
        panel.add(back);

        MatchCreatorController mc = new MatchCreatorController(frame,panel,nameMatch, back, create, name,maxSize,timeMatch,questionsSize, matches, sender, mm);

        panel.setVisible(true);

    }
    public JPanel getPanel(){
        return this.panel;
    }
}
