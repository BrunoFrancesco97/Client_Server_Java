package org.example.view;

import org.example.controller.ResultsController;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultsView {
    private JPanel results;
    public ResultsView(JFrame frame, String name, Score score, Sender sender, MatchChecker mm){
        results = new JPanel();
        int size = score.questions.size();
        results.setLayout(new GridLayout(3,1));
        results.setBorder(new EmptyBorder(25,25,25,25));
        JLabel scoreLabel = new JLabel("RESULTS");
        results.add(scoreLabel);
        Font font = scoreLabel.getFont();
        scoreLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        JScrollPane scrollable = new JScrollPane();
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        int i = 1;
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(size*5,1));
        for(Question q : score.questions){
            JLabel el1 = new JLabel(i+". Question: "+q.quest);
            el1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
            content.add(el1);
            JLabel el2 = new JLabel("Expected: "+q.answer);
            content.add(el2);
            JLabel el3 = new JLabel("Given: "+q.given);
            content.add(el3);
            JLabel el4 = new JLabel("Is correct?: "+q.correct);
            content.add(el4);
            JSeparator sp = new JSeparator();
            content.add(sp);
            i++;
        }
        scrollable.setViewportView(content);
        results.add(scrollable);
        JButton button = new JButton("Return to home");
        ResultsController rc = new ResultsController(frame,results,button,name,sender, mm);
        results.add(button);
        results.setVisible(true);
    }
    public JPanel getPanel(){
        return this.results;
    }
}
