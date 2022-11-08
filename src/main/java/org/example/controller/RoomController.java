package org.example.controller;

import org.example.model.*;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RoomController {
    public RoomController(JFrame frame, JPanel panel, JButton back, JButton start, JPanel content, String name, String nameMatch, ArrayList<Match> matches, Match match, Sender sender, MatchChecker mm, Timer t){
        if(!match.host.name.equals(name)){
            back.setText("Exit");
        }
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Message responsef = sender.send(new Message<>(name, "UPDATE_PLAYERS",nameMatch));
                System.out.println(responsef);
                if(responsef != null && responsef.getMessage() != null && responsef.getEvent().equals("UPDATE_PLAYERS") && responsef.getMessage() instanceof ArrayList<?>){
                    ArrayList<Player> players = (ArrayList<Player>) responsef.getMessage();
                    content.removeAll();
                    content.setLayout(new GridLayout((players.size()),1));
                    int i = 1;
                    System.out.println(players);
                    for(Player pp : players){
                        JPanel contentPl = new JPanel();
                        JLabel el1 = new JLabel(i+". Name: ");
                        Font font = el1.getFont();
                        el1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
                        contentPl.add(el1);
                        JLabel el2 = new JLabel(pp.name);
                        contentPl.add(el2);
                        JSeparator sp = new JSeparator();
                        contentPl.add(sp);
                        i++;
                        contentPl.setVisible(true);
                        content.add(contentPl);
                    }
                    content.setVisible(true);
                    frame.revalidate();
                    frame.repaint();
                }else{
                    System.out.println("SONO QUI");
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();
                    t.cancel();
                }
            }
        };
        t.scheduleAtFixedRate(tt,1000,2500);

        back.addActionListener(e -> {
            if(match.host.name.equals(name)){
                Message response = sender.send(new Message(name, "MATCH_REMOVER",nameMatch));
                if(response != null && response.getMessage().equals("ok")){
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();
                    t.cancel();
                }
            }else{
                Message response = sender.send(new Message(name, "REMOVE_PLAYER",nameMatch));
                frame.remove(panel);
                frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                frame.validate();
                t.cancel();
            }
        });
        /*start.addActionListener(e -> {
            Message response = sender.send(new Message(name, "FRIENDLY_START",nameMatch));
            Question q = (Question) response.getMessage();
            frame.remove(panel);
            frame.add(new QuestionView(frame, name, q, sender, mm).getPanel());
            frame.validate();
        });*/
    }
}
