package org.example.view;

import org.example.controller.RoomController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Player;
import org.example.utils.Sender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class RoomView {
    private JPanel panel;
    public RoomView(JFrame frame, String name, ArrayList<Match> matches, Match match, Sender sender, MatchChecker mm, boolean ready){
        Timer t = new Timer();
        panel = new JPanel();
        if(match.getHost().name.equals(name) || !ready) {
            panel.setLayout(new GridLayout(6,1));
        }else{
            panel.setLayout(new GridLayout(5,1));
        }

        JLabel matchName = new JLabel(match.getName());
        Font font = matchName.getFont();
        matchName.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(matchName);
        JLabel title = new JLabel("Players");
        panel.add(title);
        JLabel description = new JLabel("Max size: "+match.getSize());
        panel.add(description);
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

        RoomController rc = new RoomController(frame, panel, close, start, content, name, matches, match, sender, mm, t, ready);
        panel.setVisible(true);
    }
    public JPanel getPanel(){
        return this.panel;
    }
}
