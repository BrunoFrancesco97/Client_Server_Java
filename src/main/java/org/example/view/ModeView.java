package org.example.view;

import org.example.controller.ModeController;
import org.example.model.MatchChecker;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModeView {
    private JPanel mode;
    public ModeView(JFrame frame, String name, Sender sender, MatchChecker mm){
        mode = new JPanel();
        mode.setLayout(new GridLayout(6,1));

        mode.setBorder(new EmptyBorder(25,25,25,25));

        JLabel modeLabel1 = new JLabel("Choose your game mode");
        Font font = modeLabel1.getFont();
        modeLabel1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        mode.add(modeLabel1);

        JLabel modeLabel2 = new JLabel("Welcome "+name+"! Select the mode you want to play.");
        mode.add(modeLabel2);

        JRadioButton[] list = new JRadioButton[]{
                new JRadioButton("Practice Mode", true),
                new JRadioButton("Friendly Mode"),
                new JRadioButton("Tournament Mode")};
        ButtonGroup group = new ButtonGroup();
        for(JRadioButton el : list){
            group.add(el);
            mode.add(el);
        }
        JButton select = new JButton("Select");

        mode.add(select);


        ModeController mc = new ModeController(frame,mode,select,list,name,sender, mm);
        mode.setVisible(true);
    }

    public JPanel getPanel(){
        return this.mode;
    }
}
