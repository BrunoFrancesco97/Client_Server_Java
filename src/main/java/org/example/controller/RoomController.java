package org.example.controller;

import org.example.model.*;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;
import org.example.view.QuestionView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RoomController {
    public RoomController(JFrame frame, JPanel panel, JButton back, JButton start, JPanel content, String name, ArrayList<Match> matches, Match match, Sender sender, MatchChecker mm, Timer t){
        if(!match.host.name.equals(name)){
            back.setText("Exit");
        }
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Message responsef = sender.sendAndRead(new Message<>(name, "UPDATE_PLAYERS",match.name));
                System.out.println("R "+responsef);
                if(responsef != null && responsef.getMessage() != null && responsef.getEvent().equals("UPDATE_PLAYERS") && responsef.getMessage() instanceof Match){
                    Match mmm = (Match) responsef.getMessage();
                    if(mmm.players.size() > 0){
                        if(!mmm.available){
                            t.cancel();
                            Message responseD = sender.sendAndRead(new Message<>(name, "DROP_QUESTION",match.name));
                            if(responseD != null && responseD.getMessage() != null && responseD.getMessage() instanceof Question){
                                mm.setGoingOn(true);
                                frame.remove(panel);
                                frame.add(new QuestionView(frame, name, (Question) responseD.getMessage(), sender, mm).getPanel());
                                frame.validate();
                            }
                        }else{
                            content.removeAll();
                            content.setLayout(new GridLayout((mmm.players.size()),1));
                            int i = 1;
                            System.out.println(mmm.players);
                            for(Player pp : mmm.players){
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
                        }
                    }else{
                        t.cancel();
                        frame.remove(panel);
                        frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                        frame.validate();
                    }
                }else{
                    t.cancel();
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();

                }
            }
        };
        t.scheduleAtFixedRate(tt,10,500);

        back.addActionListener(e -> {
            if(match.host.name.equals(name)){
                Message response = sender.sendAndRead(new Message(name, "MATCH_REMOVER",match.name));
                if(response != null && response.getMessage() != null && response.getEvent().equals("MATCH_REMOVER") && response.getMessage().equals("ok")){
                    t.cancel();
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();
                }
            }else{
                t.cancel();
                sender.send(new Message(name, "REMOVE_PLAYER",match.name));
                frame.remove(panel);
                frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                frame.validate();
            }
        });
        if(start != null){
            start.addActionListener(e -> {
                sender.send(new Message(name, "FRIENDLY_START",match.name));
            });
        }
    }
}
