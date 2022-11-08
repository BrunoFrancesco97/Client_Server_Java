package org.example.controller;

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
                if(response.getMessage() != null && response.getMessage() instanceof String){
                    frame.remove(login);
                    frame.add(new AlertView(frame,name, (String) response.getMessage(),sender, mm).getPanel());
                    frame.validate();
                }else{
                    frame.remove(login);
                    frame.add(new ModeView(frame,name,sender, mm).getPanel());
                    frame.validate();
                }
            }
        });
    }
}
