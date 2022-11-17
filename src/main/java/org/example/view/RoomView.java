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
        if(match.getHost().name.equals(name) || !ready) {
            panel.setLayout(new GridLayout(8,1));
        }else{
            panel.setLayout(new GridLayout(7,1));
        }
        panel.setBorder(new EmptyBorder(25,25,25,25));
        JLabel matchName = new JLabel(match.getName());
        Font font = matchName.getFont();
        matchName.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(matchName);
        JLabel description = new JLabel("Max size: "+match.getSize());
        panel.add(description);
        JLabel questionsLabel = new JLabel("Number of questions: "+questions);
        panel.add(questionsLabel);
        JLabel durationLabel = new JLabel("Maximum duration in minutes: "+time+":00");
        panel.add(durationLabel);
        JLabel title = new JLabel("Players");
        panel.add(title);
        JPanel content = new JPanel();
        JScrollPane scrollable = new JScrollPane();
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
        panel.add(scrollable);
        JButton start = null;
        if(!ready){
            start = new JButton("Ready");
            panel.add(start);
        }
        if(match.getHost().name.equals(name) && ready && readyness == match.getPlayers().size()){
            start = new JButton("Start match");
            panel.add(start);
        }
        JButton close = new JButton("Close match");
        panel.add(close);

        RoomController rc = new RoomController(frame, panel, close, start, content, name, matches, match, sender, mm, t, ready, time, questions);
        panel.setVisible(true);
    }
    public JPanel getPanel(){
        return this.panel;
    }
}
