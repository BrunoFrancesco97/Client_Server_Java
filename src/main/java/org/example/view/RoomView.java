package org.example.view;

import org.example.controller.RoomController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Player;
import org.example.utils.Sender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RoomView {
    private JPanel panel;
    public RoomView(JFrame frame, String name, String nameMatch, ArrayList<Match> matches, ArrayList<Player> players, Sender sender, MatchChecker mm){
        panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        JLabel matchName = new JLabel(nameMatch);
        Font font = matchName.getFont();
        matchName.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(matchName);
        JLabel title = new JLabel("Players");
        panel.add(title);
        JPanel content = new JPanel();
        JScrollPane scrollable = new JScrollPane();
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.setLayout(new GridLayout(players.size()*3,1));
        int i = 1;
        for(Player p : players){
            JLabel el1 = new JLabel(i+". Name: ");
            el1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
            content.add(el1);
            JLabel el2 = new JLabel(p.name);
            content.add(el2);
            JSeparator sp = new JSeparator();
            content.add(sp);
            i++;
        }
        scrollable.setViewportView(content);
        panel.add(scrollable);

        JButton start = new JButton("Start match");
        panel.add(start);
        JButton close = new JButton("Close match");
        panel.add(close);

        RoomController rc = new RoomController(frame, panel, close, start, name, nameMatch, matches, sender, mm);
        panel.setVisible(true);
    }
    public JPanel getPanel(){
        return this.panel;
    }
}
