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
        question.setLayout(new GridLayout(5,1));
        question.setBorder(new EmptyBorder(25,25,25,25));
        if(timeLabel != null)
            question.add(timeLabel);
        JLabel questionLabel = new JLabel("Question "+iterator+": ");
        question.add(questionLabel);

        JLabel questionLabel2 = new JLabel(q.quest);
        Font font = questionLabel2.getFont();
        questionLabel2.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize()));
        question.add(questionLabel2);


        JTextField result = new JTextField(20);
        question.add(result);

        JButton sendButton = new JButton("Send answer");
        question.add(sendButton);
        mm.setQuestion(q);
        question.setVisible(true);
        QuestionController qc = new QuestionController(frame,question,sendButton,result,q, name, sender, mm, lecit, tQuiz, seconds, timeLabel,time, questions, iterator);


    }

    public JPanel getPanel(){
        return question;
    }
}
