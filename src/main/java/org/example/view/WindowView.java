package org.example.view;

import org.example.controller.WindowController;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowView {
    public WindowView(Sender sender){
        JFrame frame = new JFrame();
        frame.setTitle("Play game!");
        frame.setSize(new Dimension(600,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MatchChecker mm = new MatchChecker();

        LoginView login = new LoginView(frame,sender,mm);
        frame.add(login.getPanel());

        WindowController wc = new WindowController(frame,mm,sender);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

//TODO: L'APP NON SI CHIUDE NELLA PAGINA DI LOGIN