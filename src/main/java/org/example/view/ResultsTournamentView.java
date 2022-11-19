package org.example.view;

import org.example.controller.ResultsController;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultsTournamentView {
    private JPanel results;
    public ResultsTournamentView(JFrame frame, String name, ArrayList<Score> scores, Sender sender, MatchChecker mm, boolean casistic){
        results = new JPanel();
        results.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.insets = new Insets(5,5,5,5);
        gcc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Results");
        gcc.gridx = 0;
        gcc.gridy = 0;
        results.add(title,gcc);

        results.setVisible(true);
    }

    public JPanel getPanel(){
        return this.results;
    }
}
