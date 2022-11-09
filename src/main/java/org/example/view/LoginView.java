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
        login.setLayout(new GridLayout(4,1));
        login.setBorder(new EmptyBorder(25,25,25,25));
        JLabel loginLabel = new JLabel("LOG IN");
        Font font = loginLabel.getFont();
        loginLabel.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        login.add(loginLabel);
        login.add(new JLabel("Enter your username"));
        JTextField nameJ = new JTextField(20);
        login.add(nameJ);
        JButton loginButton = new JButton("Log In");
        login.add(loginButton);
        LoginController lc = new LoginController(frame, login, loginButton, nameJ,sender, mm);

        login.setVisible(true);
    }
    public JPanel getPanel(){
        return this.login;
    }
}
