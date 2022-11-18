package org.example.view;

import org.example.controller.ModeController;
import org.example.model.MatchChecker;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Timer;

public class ModeView {
    private JPanel mode;
    public ModeView(JFrame frame, String name, Sender sender, MatchChecker mm){
        print(frame,name,sender,mm);
    }

    private void print(JFrame frame, String name, Sender sender, MatchChecker mm){
        mode = new JPanel();
        mode.setLayout(new GridBagLayout());
        GridBagConstraints gcc = new GridBagConstraints();
        gcc.insets = new Insets(15,15,15,15);
        gcc.anchor = GridBagConstraints.WEST;

        JLabel modeLabel1 = new JLabel("Choose your game mode");
        Font font = modeLabel1.getFont();
        modeLabel1.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        gcc.gridx = 0;
        gcc.gridy = 0;
        gcc.ipady = 60;
        mode.add(modeLabel1,gcc);
        gcc.ipady = 0;
        JLabel modeLabel2 = new JLabel("Welcome "+name+"! Select the mode you want to play.");
        gcc.gridx = 0;
        gcc.gridy = 1;
        mode.add(modeLabel2,gcc);

        JRadioButton[] list = new JRadioButton[]{
                new JRadioButton("Practice Mode", true),
                new JRadioButton("Friendly Mode"),
                new JRadioButton("Tournament Mode")};
        ButtonGroup group = new ButtonGroup();
        int i = 2;
        for(JRadioButton el : list){
            group.add(el);
            gcc.gridx = 0;
            gcc.gridy = i;
            mode.add(el,gcc);
            i++;
        }


        JButton select = new JButton("Select");
        select.setPreferredSize(new Dimension(120,40));
        gcc.gridx = 0;
        gcc.gridy = 6;
        mode.add(select,gcc);


        ModeController mc = new ModeController(frame,mode,select,list,name,sender, mm);
        mode.setVisible(true);
    }
    public JPanel getPanel(){
        return this.mode;
    }
}
