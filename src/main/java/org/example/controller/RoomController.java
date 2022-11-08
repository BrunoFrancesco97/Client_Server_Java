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

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Update");
                Message responsef = sender.send(new Message<>(name, "UPDATE_PLAYERS",nameMatch));
                if(responsef != null && responsef.getMessage() != null){
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
                    //TODO: IL MATCH E' STATO CANCELLATO DEVO USCIRE DA QUESTA VIEW
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();
                    t.cancel();
                }
            }
        };
        t.scheduleAtFixedRate(tt,10,500);

        back.addActionListener(e -> {
            Message response = sender.send(new Message(name, "MATCH_REMOVER",nameMatch));
            if(response != null && response.getMessage().equals("ok")){
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
