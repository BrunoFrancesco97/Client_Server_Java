package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Rank;
import org.example.utils.Sender;
import org.example.view.ModeView;

import javax.swing.*;
import java.util.ArrayList;

public class ResultsTournamentController{
    public ResultsTournamentController(JFrame frame, JPanel results, JButton jb, String name, Sender sender, MatchChecker mm, ArrayList<Rank> ranks){
        jb.addActionListener(e -> {
            sender.send(new Message(name, "COUNT_TOURNAMENT"));
            frame.remove(results);
            frame.add(new ModeView(frame,name,sender, mm).getPanel());
            frame.validate();
        });
        float min = ranks.get(0).points;
        String nameP = ranks.get(0).name;
        for(Rank r : ranks){
            if(r.points < min)
                nameP = r.name;
        }
        if(nameP.equals(name)){
            JOptionPane.showMessageDialog(frame,"You have won the tournament!","Winner!",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(frame,"You have lost the tournament","You will win next time :(",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

