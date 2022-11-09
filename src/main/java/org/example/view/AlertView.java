package org.example.view;

import org.example.controller.AlertController;
import org.example.controller.LoginController;
import org.example.model.MatchChecker;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlertView {
    private JPanel alert;
    public AlertView(JFrame frame, String name,  String type, Sender sender, MatchChecker mm){
        alert = new JPanel();
        alert.setBorder(new EmptyBorder(25,25,25,25));
        alert.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel attentionLabel = new JLabel("Warning!");
        Font font = attentionLabel.getFont();
        attentionLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        alert.add(attentionLabel, c);

        JLabel attentionLabel2 = new JLabel("A previous not finished match of type "+type+" is found");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        alert.add(attentionLabel2, c);

        JLabel attentionLabel3 = new JLabel("Do you want to resume it?");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        alert.add(attentionLabel3, c);

        JButton buttonYes = new JButton("Yes");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        alert.add(buttonYes, c);

        JButton buttonNo = new JButton("No");
        c.gridx = 1;
        c.gridy = 3;
        alert.add(buttonNo, c);

        AlertController lc = new AlertController(frame, alert, name, type, buttonYes, buttonNo, sender, mm);

        alert.setVisible(true);
    }

    public JPanel getPanel(){
        return this.alert;
    }

}
