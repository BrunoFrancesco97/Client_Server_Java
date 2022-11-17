package org.example.view;

import org.example.controller.RoomController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Player;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class RoomView {
    private JPanel panel;
    public RoomView(JFrame frame, String name, ArrayList<Match> matches, Match match, Sender sender, MatchChecker mm, boolean ready, int time, int questions){
        Timer t = new Timer();
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.insets = new Insets(5,0,5,5);
        gcc.anchor = GridBagConstraints.WEST;

        JLabel matchName = new JLabel("Name of the match: "+match.getName());
        gcc.ipady = 30;
        Font font = matchName.getFont();
        matchName.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        gcc.gridx = 0;
        gcc.gridy = 0;
        panel.add(matchName,gcc);

        gcc.ipady = 0;

        JLabel description = new JLabel("Max size: "+match.getSize());
        gcc.gridx = 0;
        gcc.gridy = 1;
        panel.add(description,gcc);

        JLabel questionsLabel = new JLabel("Number of questions: "+questions);
        gcc.gridx = 0;
        gcc.gridy = 2;
        panel.add(questionsLabel,gcc);

        JLabel durationLabel = new JLabel("Maximum duration in minutes: "+time+":00");
        gcc.gridx = 0;
        gcc.gridy = 3;
        panel.add(durationLabel,gcc);

        JLabel title = new JLabel("Players:");
        gcc.gridx = 0;
        gcc.gridy = 4;
        panel.add(title,gcc);

        JPanel content = new JPanel();
        JScrollPane scrollable = new JScrollPane();
        scrollable.setPreferredSize(new Dimension(350,150));
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.setLayout(new GridLayout(match.getPlayers().size(),1));
        int i = 1;
        int readyness = 0;
        for(Player p : match.getPlayers()){
            JPanel contentPl = new JPanel();
            JLabel el1 = new JLabel(i+". Name: ");
            el1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
            contentPl.add(el1);
            JLabel el2 = new JLabel(p.name+ " - Ready: "+p.isReady());
            contentPl.add(el2);
            JSeparator sp = new JSeparator();
            contentPl.add(sp);
            i++;
            if(p.isReady())
                readyness++;
            contentPl.setVisible(true);
            content.add(contentPl);
        }
        scrollable.setViewportView(content);
        gcc.gridx = 0;
        gcc.gridy = 5;
        panel.add(scrollable,gcc);


        JPanel pp = new JPanel();
        pp.setLayout(new GridBagLayout());
        gcc.gridx = 0;
        gcc.gridy = 6;
        panel.add(pp,gcc);

        JButton start = null;
        if(!ready){
            start = new JButton("Ready");
            start.setPreferredSize(new Dimension(120,50));
            gcc.gridx = 1;
            gcc.gridy = 0;
            pp.add(start,gcc);
        }
        if(match.getHost().name.equals(name) && ready && readyness == match.getPlayers().size()){
            start = new JButton("Start match");
            start.setPreferredSize(new Dimension(120,50));
            gcc.gridx = 1;
            gcc.gridy = 0;
            pp.add(start,gcc);
        }

        JButton close = new JButton("Close match");
        close.setPreferredSize(new Dimension(120,50));
        gcc.gridx = 0;
        gcc.gridy = 0;
        pp.add(close,gcc);

        RoomController rc = new RoomController(frame, panel, close, start, content, name, matches, match, sender, mm, t, ready, time, questions, gcc, pp);
        panel.setVisible(true);
    }
    public JPanel getPanel(){
        return this.panel;
    }
}
