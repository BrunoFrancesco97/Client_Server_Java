package org.example.controller;

import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;
import org.example.view.AlertView;
import org.example.view.ModeView;

import javax.swing.*;

public class LoginController {
    public LoginController(JFrame frame, JPanel login, JButton jb, JTextField nameJ, Sender sender, MatchChecker mm){
        jb.addActionListener(e -> {
            String name = nameJ.getText();
            if(name != null && name.length() > 0){
                mm.setName(name);
                Message response = sender.sendAndRead(new Message(name, "NAME"));
                if(response.getMessage() != null && response.getMessage() instanceof Match){
                    frame.remove(login);
                    mm.setPosition(((Match) response.getMessage()).getPlayer(name).getIndexLastQuestion());
                    frame.add(new AlertView(frame,name, ((Match) response.getMessage()).getType(),sender, mm).getPanel());
                    frame.validate();
                }else{
                    if(response.getMessage() != null && response.getMessage() instanceof String){
                        JOptionPane.showMessageDialog(frame,"A user with this name is already connected","Warning!",JOptionPane.WARNING_MESSAGE);
                    }else{
                        frame.remove(login);
                        frame.add(new ModeView(frame,name,sender, mm).getPanel());
                        frame.validate();
                    }
                }
            }
        });
    }
}
