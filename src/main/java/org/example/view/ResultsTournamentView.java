package org.example.view;

import org.example.controller.ResultsController;
import org.example.controller.ResultsTournamentController;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.example.model.Rank;
import org.example.model.Score;
import org.example.utils.Sender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultsTournamentView {
    private JPanel results;
    public ResultsTournamentView(JFrame frame, String name, ArrayList<Rank> ranks, Sender sender, MatchChecker mm){
        results = new JPanel();
        results.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.insets = new Insets(5,5,5,5);
        gcc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Results");
        gcc.gridx = 0;
        gcc.gridy = 0;
        results.add(title,gcc);

        JScrollPane scrollable = new JScrollPane();
        scrollable.setPreferredSize(new Dimension(400,350));
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(ranks.size(),1));
        for(Rank rank : ranks){
            JPanel content2 = new JPanel();
            content2.setLayout(new GridBagLayout());
            JLabel namePlayer = new JLabel("User: "+rank.name);
            gcc.gridy=0;
            content2.add(namePlayer,gcc);
            JLabel completed = new JLabel("Points: "+rank.points);
            gcc.gridy=1;
            content2.add(completed,gcc);
            gcc.gridy=2;
            JSeparator sp = new JSeparator();
            content2.add(sp,gcc);
            content.add(content2);
        }
        scrollable.setViewportView(content);
        gcc.gridy = 1;
        results.add(scrollable,gcc);

        JButton button = new JButton("Return to home");
        button.setPreferredSize(new Dimension(120,50));
        gcc.gridy = 2;
        results.add(button,gcc);

        ResultsTournamentController rc = new ResultsTournamentController(frame, results, button, name, sender, mm, ranks);
        results.setVisible(true);
    }

    public JPanel getPanel(){
        return this.results;
    }
}
