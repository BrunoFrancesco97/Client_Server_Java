package org.example.view;

import org.example.controller.LoginController;
import org.example.model.MatchChecker;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView {
    private JPanel login;
    public LoginView(JFrame frame, Sender sender, MatchChecker mm){
        login = new JPanel();

        login.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5,5,5,5);

        JLabel loginLabel = new JLabel("LOG IN");
        Font font = loginLabel.getFont();
        loginLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        c.ipady = 60;
        c.gridx = 0;
        c.gridy = 0;
        login.add(loginLabel,c);

        c.ipady = 15;
        c.gridx = 0;
        c.gridy = 1;
        login.add(new JLabel("Enter your username so to play fantastic games alone or with your friends!"),c);



        JTextField nameJ = new JTextField(20);
        c.gridx = 0;
        c.gridy = 3;
        login.add(nameJ,c);

        JLabel username = new JLabel("Username:");
        username.setLabelFor(nameJ);
        c.gridx = 0;
        c.gridy = 2;
        login.add(username,c);

        JSeparator sp = new JSeparator();
        c.gridx = 0;
        c.gridy = 4;
        login.add(sp,c);

        JButton loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(120,40));
        c.gridx = 0;
        c.gridy = 5;
        login.add(loginButton,c);

        LoginController lc = new LoginController(frame, login, loginButton, nameJ,sender, mm);

        login.setVisible(true);
    }
    public JPanel getPanel(){
        return this.login;
    }
}
