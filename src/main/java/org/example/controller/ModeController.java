package org.example.controller;

import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.model.Question;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;
import org.example.view.QuestionView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;

public class ModeController {
    public ModeController(JFrame frame, JPanel mode, JButton jb, JRadioButton[] list, String name, Sender sender, MatchChecker mm){
        this.controller(frame,mode,jb,list,name,sender,mm);
    }

    private void controller(JFrame frame, JPanel mode, JButton jb, JRadioButton[] list, String name, Sender sender, MatchChecker mm){
        jb.addActionListener(e -> {
            String value = null;
            for(JRadioButton el : list){
                if(el.isSelected())
                    value = el.getText();
            }
            switch (value.toLowerCase()){
                case "practice mode":
                    String[] options = {"Yes","No"};
                    int result = JOptionPane.showOptionDialog(frame , "A game will start soon.\n Do you want to start it now?", "Question",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if(result == 0){
                        mm.setType("practice");
                        mm.setGoingOn(true);
                        Message response = sender.sendAndRead(new Message<>(name, "START","practice"));
                        Question q = (Question) response.getMessage();
                        frame.remove(mode);
                        frame.add(new QuestionView(frame,name,q,sender, mm, true, null, null, null).getPanel());
                        frame.validate();
                    }
                    break;
                case "friendly mode":
                    mm.setType("friendly");
                    Message responsef = sender.sendAndRead(new Message<>(name, "START","friendly"));
                    if(responsef != null && responsef.getEvent().equals("LIST") && responsef.getMessage() != null && responsef.getMessage() instanceof  ArrayList<?>){
                        ArrayList<Match> matches = (ArrayList<Match>) responsef.getMessage();
                        frame.remove(mode);
                        frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
                        frame.validate();
                    }
                    break;
                case "tournament mode":
                    break;
            }
        });
    }
}
