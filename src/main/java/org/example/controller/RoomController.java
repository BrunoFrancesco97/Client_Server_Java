package org.example.controller;

import org.example.model.*;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;
import org.example.view.QuestionView;
import org.example.view.RoomView;
import org.example.view.TournamentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomController {
    public RoomController(JFrame frame, JPanel panel, JButton back, JButton start, JButton readyB, JPanel content, String name, ArrayList<Match> matches, Match match, Sender sender, MatchChecker mm, boolean ready, int time, int questions, GridBagConstraints gcc, JPanel pp){
        if(!match.getHost().name.equals(name)){
            back.setText("Exit");
        }
        Timer t = new Timer(); //T is the timer that checks if players are ready or not
        Timer t2 = new Timer(); //T2 is the timer that checks if 5 seconds are passed if all players are ready
        TimerTask tt = new TimerTask() {
            AtomicBoolean startAdded = new AtomicBoolean(false);
            AtomicInteger timerStart = new AtomicInteger(0);

            @Override
            public void run() {
                Message responsef = sender.sendAndRead(new Message<>(name, "UPDATE_PLAYERS",match.getName()));
                if(responsef != null && responsef.getMessage() != null && responsef.getEvent().equals("UPDATE_PLAYERS") && responsef.getMessage() instanceof Match){
                    Match mmm = (Match) responsef.getMessage();
                    if(mmm.getPlayers().size() > 0){
                        if(!mmm.isAvailable()){ //Match started
                            t.cancel();
                            t2.cancel();
                            Message responseD = sender.sendAndRead(new Message<>(name, "DROP_QUESTION",match.getName()));
                            if(responseD != null && responseD.getMessage() != null && responseD.getMessage() instanceof Question){
                                Timer tQuiz = new Timer();
                                AtomicInteger seconds = new AtomicInteger(0);
                                mm.setGoingOn(true);
                                mm.setMatch(match.getName());
                                mm.setPosition(1);
                                if(mm.getType().equals("friendly")){
                                    frame.remove(panel);
                                    frame.add(new QuestionView(frame, name, (Question) responseD.getMessage(), sender, mm, false, tQuiz, seconds, new JLabel(), time, questions, 1).getPanel());
                                    frame.validate();
                                } else if (mm.getType().equals("tournament")) {
                                    frame.remove(panel);
                                    frame.add(new TournamentView(frame, name, (Question) responseD.getMessage(), sender, mm, new JLabel(), questions, 1, false).getPanel());
                                    frame.validate();
                                }

                            }
                        }else{ //Match is not started but update request is made
                            content.removeAll();
                            content.setLayout(new GridLayout((mmm.getPlayers().size()),1));
                            int i = 1;
                            int readyness = printerCicle(mmm,i,content, 0);
                            if(readyness == mmm.getPlayers().size()){ //All players are ready
                                if(mmm.getHost().name.equals(name) && ready && !startAdded.get()){
                                    TimerTask tt2 = new TimerTask() {
                                        @Override
                                        public void run() {
                                            int val = timerStart.addAndGet(1);
                                            if(val > 5){
                                                t2.cancel();
                                                sender.send(new Message(name, "FRIENDLY_START",match.getName()));
                                            }
                                        }
                                    };
                                    t2.schedule(tt2, 400,1000);
                                    startAdded.set(true);

                                }
                            }else{ //NOt all players are ready
                                timerStart.set(0);


                            }
                            content.setVisible(true);
                            frame.revalidate();
                            frame.repaint();
                        }
                    }else{ //There are no players in the room because I left
                        t2.cancel();
                        t.cancel();
                        frame.remove(panel);
                        frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                        frame.validate();
                    }
                }else{ //Not valid response
                    t2.cancel();
                    t.cancel();
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();

                }
            }
        };
        t.scheduleAtFixedRate(tt,10,500);

        back.addActionListener(e -> {
            if(match.getHost().name.equals(name)){
                Message response = sender.sendAndRead(new Message(name, "MATCH_REMOVER",match.getName()));
                if(response != null && response.getMessage() != null && response.getEvent().equals("MATCH_REMOVER") && response.getMessage().equals("ok")){
                    t.cancel();
                    t2.cancel();
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();
                }
            }else{
                t.cancel();
                sender.send(new Message(name, "REMOVE_PLAYER",match.getName()));
                frame.remove(panel);
                frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                frame.validate();
            }
        });

        readyB.addActionListener(e -> {
            sender.send(new Message(name, "UPDATE_READY", true));
            readyB.setVisible(false);
            start.setVisible(true);
        });


        if(start != null){
            start.addActionListener(e -> {
                sender.send(new Message(name, "FRIENDLY_START",match.getName()));
            });
        }
    }

    private int printerCicle(Match match, int i, JPanel content, int readyness){
        for(Player p : match.getPlayers()){
            JPanel contentPl = new JPanel();
            JLabel el1 = new JLabel(i+". Name: ");
            el1.setFont(new Font(el1.getFont().getFontName(),Font.BOLD,el1.getFont().getSize()));
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
        return readyness;
    }

    /*private void adderActionListener(boolean ready, Timer t, String name, Sender sender, JFrame frame, JPanel panel, ArrayList<Match> matches, Match match, MatchChecker mm, int time, int questions){
        if(!ready){
            t.cancel();
            sender.send(new Message(name, "UPDATE_READY", true));
            frame.remove(panel);
            frame.add(new RoomView(frame, name, matches, match, sender, mm, true, time, questions).getPanel());
            frame.validate();
        }else{
            sender.send(new Message(name, "FRIENDLY_START",match.getName()));
        }
    }*/
}
