package org.example.controller;

import org.example.model.*;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;
import org.example.view.QuestionView;
import org.example.view.TournamentView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomController {
    private static Match match;
    public RoomController(JFrame frame, JPanel panel, JButton back, JButton start, JButton readyB, JPanel content, String name, ArrayList<Match> matches, Match match, Sender sender, MatchChecker mm, int time, int questions, JLabel crome){
        RoomController.match = match;
        if(!RoomController.match.getHost().name.equals(name)){
            back.setText("Exit");
        }
        Timer t = new Timer(); //T is the timer that checks if players are ready or not
        TimerTask tt = new TimerTask() {
            AtomicInteger timerStart = new AtomicInteger(5);

            @Override
            public void run() {
                Message responsef = sender.sendAndRead(new Message<>(name, "UPDATE_PLAYERS",match.getName()));
                if(responsef != null && responsef.getMessage() != null && responsef.getEvent().equals("UPDATE_PLAYERS") && responsef.getMessage() instanceof Match){
                    Match mmm = (Match) responsef.getMessage();
                    RoomController.match = mmm;
                    if(mmm.getPlayers().size() > 0){
                        if(!mmm.isAvailable()){ //Match started
                            t.cancel();
                            Message responseD = sender.sendAndRead(new Message<>(name, "DROP_QUESTION",RoomController.match.getName()));
                            if(responseD != null && responseD.getMessage() != null && responseD.getMessage() instanceof Question){
                                Timer tQuiz = new Timer();
                                AtomicInteger seconds = new AtomicInteger(0);
                                mm.setGoingOn(true);
                                mm.setMatch(RoomController.match.getName());
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

                            int readyness = printerCicle(mmm,1,content);
                            if(readyness == mmm.getPlayers().size()){ //All players are ready
                                crome.setVisible(true);
                                int val = timerStart.decrementAndGet();
                                crome.setText("Seconds until start: "+val);
                                if(mmm.getHost().name.equals(name)){
                                    start.setVisible(true);
                                    if(val == 0){
                                        sender.send(new Message(name, "FRIENDLY_START",RoomController.match.getName()));
                                    }
                                }
                            }else{ //NOt all players are ready
                                timerStart.set(5);
                                if(mmm.getHost().name.equals(name)){
                                    start.setVisible(false);
                                    crome.setVisible(false);
                                }
                            }
                            content.setVisible(true);
                            frame.revalidate();
                            frame.repaint();
                        }
                    }else{ //There are no players in the room because I left
                        t.cancel();
                        frame.remove(panel);
                        frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                        frame.validate();
                    }
                }else{ //Not valid response
                    t.cancel();
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();

                }
            }
        };
        t.scheduleAtFixedRate(tt,100,1000);






        back.addActionListener(e -> {
            if(RoomController.match.getHost().name.equals(name)){
                Message response = sender.sendAndRead(new Message(name, "MATCH_REMOVER",RoomController.match.getName()));
                if(response != null && response.getMessage() != null && response.getEvent().equals("MATCH_REMOVER") && response.getMessage().equals("ok")){
                    t.cancel();
                    frame.remove(panel);
                    frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                    frame.validate();
                }
            }else{
                t.cancel();
                sender.send(new Message(name, "REMOVE_PLAYER",RoomController.match.getName()));
                frame.remove(panel);
                frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                frame.validate();
            }
        });

        readyB.addActionListener(e -> {
            sender.send(new Message(name, "UPDATE_READY", true));
            readyB.setVisible(false);
            if(RoomController.match.getHost().name.equals(name))
                start.setVisible(true);
        });


        start.addActionListener(e -> {
            sender.send(new Message(name, "FRIENDLY_START",RoomController.match.getName()));
        });
    }

    private int printerCicle(Match match, int i, JPanel content){
        int readiness = 0;
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
                readiness++;
            contentPl.setVisible(true);
            content.add(contentPl);
        }
        return readiness;
    }
}
