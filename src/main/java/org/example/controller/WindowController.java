package org.example.controller;

import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowController {

    public WindowController(JFrame frame, MatchChecker mm, Sender sender){
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(mm.isGoingOn()){
                    sender.send(new Message(mm.getName(),"END", mm));
                }else{
                    System.out.println(mm.getName());
                    sender.send(new Message(mm.getName(),"END"));
                }
                sender.close();
            }
        });
    }
}
