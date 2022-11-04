package org.example.controller;

import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;

import javax.swing.*;
import java.util.ArrayList;

public class RoomController {
    public RoomController(JFrame frame, JPanel panel, JButton back, JButton start, String name, String nameMatch, ArrayList<Match> matches, Sender sender, MatchChecker mm){
        back.addActionListener(e -> {
            Message response = sender.send(new Message(name, "MATCH_REMOVER",nameMatch));
            if(response != null && response.getMessage().equals("ok")){
                frame.remove(panel);
                frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                frame.validate();
            }
        });
        /*start.addActionListener(e -> {

        });*/
    }
}
