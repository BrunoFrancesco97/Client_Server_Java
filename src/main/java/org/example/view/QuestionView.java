package org.example.view;

import org.example.controller.QuestionController;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionView {
    JPanel question;
    public QuestionView(JFrame frame, String name, Question q, Sender sender, MatchChecker mm, boolean lecit, Timer tQuiz, AtomicInteger seconds, JLabel timeLabel,int time, int questions, int iterator){
        question = new JPanel();
        question.setLayout(new GridBagLayout());

        GridBagConstraints gcc = new GridBagConstraints();
        gcc.anchor = GridBagConstraints.CENTER;
        gcc.insets = new Insets(5,5,5,5);
        gcc.gridwidth = 1;

        if(timeLabel != null){
            gcc.gridx = 0;
            gcc.gridy = 0;
            gcc.ipady=60;
            question.add(timeLabel,gcc);

        }
        gcc.ipady=0;
        JLabel questionLabel = new JLabel("Question "+iterator+": ");
        gcc.gridx = 0;
        gcc.gridy = 1;
        question.add(questionLabel,gcc);

        JLabel questionLabel2 = new JLabel(q.quest);
        Font font = questionLabel2.getFont();
        questionLabel2.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        gcc.gridx = 0;
        gcc.gridy = 2;
        question.add(questionLabel2,gcc);


        JTextField result = new JTextField(20);
        gcc.gridx = 0;
        gcc.gridy = 3;
        question.add(result,gcc);

        JSeparator sp = new JSeparator();
        gcc.gridx = 0;
        gcc.gridy = 4;
        gcc.weightx = 1;
        question.add(sp,gcc);
        JButton sendButton = new JButton("Send answer");
        gcc.gridx = 0;
        gcc.gridy = 5;
        sendButton.setPreferredSize(new Dimension(120,50));
        question.add(sendButton,gcc);
        mm.setQuestion(q);
        mm.setPosition(iterator);
        question.setVisible(true);
        QuestionController qc = new QuestionController(frame,question,sendButton,result,q, name, sender, mm, lecit, tQuiz, seconds, timeLabel,time, questions, iterator);


    }

    public JPanel getPanel(){
        return question;
    }
}
