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
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.anchor = GridBagConstraints.WEST;
        gcc.insets = new Insets(5,5,5,5);

        JLabel title = new JLabel("Create a new match");
        Font font = title.getFont();
        title.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        gcc.gridx = 0;
        gcc.gridy = 0;
        panel.add(title,gcc);


        JLabel description = new JLabel("Name of the match: ");
        gcc.gridy = 2;
        panel.add(description,gcc);

        JTextField nameMatch = new JTextField(20);
        gcc.gridy = 3;
        panel.add(nameMatch,gcc);

        JLabel description1 = new JLabel("Type of the match:");
        gcc.gridy = 4;
        panel.add(description1,gcc);

        JComboBox modeList = new JComboBox(new String[]{"friendly","tournament"});
        modeList.setSelectedIndex(0);
        gcc.gridy = 5;
        panel.add(modeList,gcc);

        JLabel description2 = new JLabel("Maximum number of people allowed:  ");
        gcc.gridy = 6;
        panel.add(description2,gcc);

        SpinnerModel people = new SpinnerNumberModel(4,1,10,1);
        JSpinner maxSize = new JSpinner(people);
        gcc.gridy = 7;
        panel.add(maxSize,gcc);

        JLabel description3 = new JLabel("Maximum duration of the match in minutes:");
        gcc.gridy = 8;
        panel.add(description3,gcc);

        SpinnerModel time = new SpinnerNumberModel(1,1,10,1);
        JSpinner timeMatch = new JSpinner(time);
        gcc.gridy = 9;
        panel.add(timeMatch,gcc);


        JLabel description4 = new JLabel("Numbers of questions of the match:");
        gcc.gridy = 10;
        panel.add(description4,gcc);

        SpinnerModel questions = new SpinnerNumberModel(5,5,15,5);
        JSpinner questionsSize = new JSpinner(questions);
        gcc.gridy = 11;
        panel.add(questionsSize,gcc);

        JSeparator sp = new JSeparator();
        gcc.gridy = 12;
        panel.add(sp,gcc);

        JPanel pp = new JPanel();
        pp.setLayout(new GridBagLayout());
        gcc.gridy = 13;
        panel.add(pp,gcc);

        JButton create = new JButton("Create");
        create.setPreferredSize(new Dimension(120,50));
        gcc.gridx = 1;
        gcc.gridy = 0;
        pp.add(create,gcc);

        JButton back = new JButton("Go back");
        back.setPreferredSize(new Dimension(120,50));
        gcc.gridx = 0;
        gcc.gridy = 0;
        pp.add(back,gcc);

        MatchCreatorController mc = new MatchCreatorController(frame,panel,nameMatch, back, create, name,maxSize,timeMatch,questionsSize, modeList, matches, sender, mm);

        panel.setVisible(true);

    }
    public JPanel getPanel(){
        return this.panel;
    }
}
