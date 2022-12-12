package org.example.view;

import org.example.controller.ResultsController;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.example.model.Score;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
public class ResultsView {
    private JPanel results;
    public ResultsView(JFrame frame, String name, Score score, Sender sender, MatchChecker mm, boolean casistic){
        results = new JPanel();
        results.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.insets = new Insets(5,5,5,5);
        gcc.anchor = GridBagConstraints.WEST;

        if(!casistic && score != null){
            int size = score.questions.size();

            JLabel scoreLabel = new JLabel("RESULTS");
            gcc.gridx = 0;
            gcc.gridy = 0;
            results.add(scoreLabel,gcc);

            Font font = scoreLabel.getFont();
            scoreLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
            JScrollPane scrollable = new JScrollPane();
            scrollable.setPreferredSize(new Dimension(400,350));
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
            gcc.gridx = 0;
            gcc.gridy = 1;
            results.add(scrollable,gcc);


            JButton button = new JButton("Return to home");
            button.setPreferredSize(new Dimension(120,50));
            gcc.gridx = 0;
            gcc.gridy = 2;
            results.add(button,gcc);

            ResultsController rc = new ResultsController(frame,results,button,name,sender, mm);


        }else{
            JLabel waiting = new JLabel("Waiting for  the other players");
            gcc.gridx = 0;
            gcc.gridy = 0;
            results.add(waiting,gcc);
        }
        results.setVisible(true);
    }
    public ResultsView(JFrame frame, String name, ArrayList<Score> scores, Sender sender, MatchChecker mm, boolean casistic){
        results = new JPanel();
        results.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.insets = new Insets(5,5,5,5);
        gcc.anchor = GridBagConstraints.WEST;
        if(!casistic){
            JLabel scoreLabel = new JLabel("RESULTS");
            gcc.gridx = 0;
            gcc.gridy = 0;
            results.add(scoreLabel,gcc);

            Font font = scoreLabel.getFont();
            scoreLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));

            JScrollPane scrollable = new JScrollPane();
            scrollable.setPreferredSize(new Dimension(400,350));
            scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            JPanel content = new JPanel();
            content.setLayout(new GridLayout(scores.size(),1));
            for(Score score : scores){
                JPanel content2 = new JPanel();
                content2.setLayout(new GridLayout(score.questions.size()*7,1));
                JLabel namePlayer = new JLabel("User: "+score.name);
                namePlayer.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
                content2.add(namePlayer);
                JLabel completed = new JLabel("Completed: "+score.isCompleted());
                content2.add(completed);
                for(Question q : score.questions){
                    JLabel el1 = new JLabel("Question: "+q.quest);
                    el1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
                    content2.add(el1);
                    JLabel el2 = new JLabel("Expected: "+q.answer);
                    content2.add(el2);
                    JLabel el3 = new JLabel("Given: "+q.given);
                    content2.add(el3);
                    JLabel el4 = new JLabel("Is correct?: "+q.correct);
                    content2.add(el4);
                }
                JSeparator sp = new JSeparator();
                content2.add(sp);
                content.add(content2);
            }
            scrollable.setViewportView(content);
            gcc.gridy = 1;
            results.add(scrollable,gcc);

            JButton button = new JButton("Return to home");
            button.setPreferredSize(new Dimension(120,50));
            gcc.gridy = 2;
            results.add(button,gcc);

            ResultsController rc = new ResultsController(frame,results,button,name,sender, mm);

        }else{
            JLabel waiting = new JLabel("Waiting for  the other players");
            results.add(waiting);
        }
        results.setVisible(true);
    }
    public JPanel getPanel(){
        return this.results;
    }
}