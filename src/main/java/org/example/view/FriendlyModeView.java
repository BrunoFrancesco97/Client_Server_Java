package org.example.view;

import org.example.controller.FriendlyModeController;
import org.example.model.Match;
import org.example.model.MatchChecker;
import org.example.model.Message;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class FriendlyModeView {
    private JPanel panel;
    public FriendlyModeView(JFrame frame, String name, ArrayList<Match> matches, Sender sender, MatchChecker mm){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gcc1 = new GridBagConstraints();
        gcc1.fill = GridBagConstraints.HORIZONTAL;
        gcc1.anchor = GridBagConstraints.CENTER;
        gcc1.weightx = 0;
        gcc1.weighty = 0;
        gcc1.insets = new Insets(5,5,5,5);
        JLabel title = new JLabel("Friendly mode");
        gcc1.ipady = 60;
        gcc1.gridx = 0;
        gcc1.gridy = 0;
        Font font = title.getFont();
        title.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        panel.add(title,gcc1);

        gcc1.ipady = 0;

        JLabel description = new JLabel("Select a match or create a new one.");
        gcc1.gridx = 0;
        gcc1.gridy = 1;
        panel.add(description,gcc1);

        JScrollPane scrollable = new JScrollPane();
        gcc1.gridx = 0;
        gcc1.gridy = 2;
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(200,200));
        content.setLayout(new GridLayout((matches.size()),1));
        content.setVisible(true);
        scrollable.setViewportView(content);

        print(matches, sender, name, frame, panel, content, mm);
        panel.add(scrollable,gcc1);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gcc2 = new GridBagConstraints();
        gcc1.gridx = 0;
        gcc1.gridy = 3;
        panel.add(panel2,gcc1);

        JButton reload = new JButton("Reload");
        reload.setPreferredSize(new Dimension(160,40));
        gcc2.gridx = 0;
        gcc2.gridy = 0;
        panel2.add(reload,gcc2);

        JLabel or = new JLabel("or");
        gcc2.gridx = 0;
        gcc2.gridy = 1;
        panel2.add(or,gcc2);

        JButton back = new JButton("Go Back");
        back.setPreferredSize(new Dimension(160,40));
        gcc2.gridx = 0;
        gcc2.gridy = 2;
        panel2.add(back,gcc2);

        JButton button = new JButton("Create a new match");
        button.setPreferredSize(new Dimension(160,40));
        gcc2.gridx = 1;
        gcc2.gridy = 2;
        panel2.add(button,gcc2);



        FriendlyModeController fmc = new FriendlyModeController(frame, panel, matches, button, back, reload, content, name, sender, mm);

        content.setVisible(true);
        panel.setVisible(true);
    }

    private void print(ArrayList<Match> matches, Sender sender, String name, JFrame frame, JPanel panel, JPanel content, MatchChecker mm){
        for(Match m : matches){
            JPanel content2 = new JPanel();
            content2.setLayout(new GridLayout(5,1));
            JLabel l1 = new JLabel("Match: "+m.getName());
            content2.add(l1);
            JLabel l2 = new JLabel("Host: "+m.getHost());
            content2.add(l2);
            JLabel l3 = new JLabel("Size: "+m.getPlayers().size());
            content2.add(l3);
            JButton enter = new JButton("Enter");
            content2.add(enter);
            enter.addActionListener(e2 -> {
                Message response = sender.sendAndRead(new Message<>(name, "GET_IN",m.getName()));
                if(response != null && response.getMessage() != null && response.getEvent().equals("GET_IN") && response.getMessage() instanceof Match){
                    Match mGet = (Match) response.getMessage();
                    frame.remove(panel);
                    frame.add(new RoomView(frame, name, matches, mGet, sender, mm, false, mGet.getTime(),mGet.getNumberQuestions()).getPanel());
                    frame.validate();
                }else{
                    JOptionPane.showMessageDialog(frame,"Can't join to this match!","Warning!",JOptionPane.WARNING_MESSAGE);
                }
            });
            JSeparator js = new JSeparator();
            content2.add(js);
            content.add(content2);
            content2.setVisible(true);
        }
    }
    public JPanel getPanel(){
        return this.panel;
    }
}
