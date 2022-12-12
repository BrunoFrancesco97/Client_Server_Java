package org.example.controller;

import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;
import org.example.view.FriendlyModeView;
import org.example.view.RoomView;

import javax.swing.*;
import java.util.ArrayList;

public class MatchCreatorController {
    public MatchCreatorController(JFrame frame, JPanel panel, JTextField nameMatch, JButton back, JButton create, String name, JSpinner maxSize, JSpinner timeMatch, JSpinner questionsSize, JComboBox modeList, ArrayList<Match> matches, Sender sender, MatchChecker mm){
        create.addActionListener(e -> {
            String nameM = nameMatch.getText();
            if(nameM != null && nameM.length() > 0){
                Message response = sender.sendAndRead(new Message(name, "NAME_CHECKER",nameM));
                if(response.getMessage() != null && response.getMessage().equals("Y")){
                    JOptionPane.showMessageDialog(frame,"Game match already used!","Warning!",JOptionPane.WARNING_MESSAGE);
                }else{
                    Message response2 = sender.sendAndRead(new Message(name, "CREATE",nameM+":"+maxSize.getValue()+":"+timeMatch.getValue()+":"+questionsSize.getValue()+":"+modeList.getSelectedItem()));
                    if(response2.getMessage() != null){
                        Match match = (Match) response2.getMessage();
                        mm.setType((String) modeList.getSelectedItem());
                        mm.setGoingOn(true);
                        frame.remove(panel);
                        frame.add(new RoomView(frame, name, matches,match, sender, mm, (Integer) timeMatch.getValue(), (Integer) questionsSize.getValue()).getPanel());
                        frame.validate();
                    }
                }
            }
        });
        back.addActionListener(e -> {
            frame.remove(panel);
            frame.add(new FriendlyModeView(frame, name, matches, sender, mm).getPanel());
            frame.validate();
        });
    }
}
