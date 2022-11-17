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
        alert.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.insets = new Insets(5,5,5,5);
        JLabel attentionLabel = new JLabel("Warning!");
        Font font = attentionLabel.getFont();
        attentionLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        c.gridx = 0;
        c.gridy = 0;
        alert.add(attentionLabel, c);

        JLabel attentionLabel2 = new JLabel("A previous not finished match of type "+type+" is found");
        c.gridx = 0;
        c.gridy = 1;
        alert.add(attentionLabel2, c);

        JLabel attentionLabel3 = new JLabel("Do you want to resume it?");
        c.gridx = 0;
        c.gridy = 2;
        alert.add(attentionLabel3, c);

        JSeparator sp = new JSeparator();
        c.gridx = 0;
        c.gridy = 3;
        alert.add(sp, c);

        JPanel pp = new JPanel();
        pp.setLayout(new GridBagLayout());
        GridBagConstraints cc = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        alert.add(pp,c);

        JButton buttonYes = new JButton("Yes");
        buttonYes.setPreferredSize(new Dimension(120,50));
        cc.gridx = 0;
        cc.gridy = 0;
        buttonYes.setOpaque(true);
        pp.add(buttonYes, cc);

        JButton buttonNo = new JButton("No");
        buttonNo.setPreferredSize(new Dimension(120,50));
        cc.gridx = 1;
        cc.gridy = 0;
        pp.add(buttonNo, cc);

        AlertController lc = new AlertController(frame, alert, name, type, buttonYes, buttonNo, sender, mm);

        alert.setVisible(true);
    }

    public JPanel getPanel(){
        return this.alert;
    }

}
