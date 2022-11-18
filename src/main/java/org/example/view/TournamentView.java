package org.example.view;

import org.example.controller.QuestionController;
import org.example.controller.TournamentController;
import org.example.model.MatchChecker;
import org.example.model.Question;
import org.example.utils.Sender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class TournamentView {
    JPanel question;
    public TournamentView(JFrame frame, String name, Question q, Sender sender, MatchChecker mm, JLabel timeLabel, int questions, int iterator, boolean casistic){
        question = new JPanel();
        question.setLayout(new GridBagLayout());

        GridBagConstraints gcc = new GridBagConstraints();
        gcc.anchor = GridBagConstraints.CENTER;

        gcc.insets = new Insets(5,5,5,5);

        JButton sendButton = null;
        ArrayList<JRadioButton> elements = new ArrayList<>();

        if(!casistic){
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

            ArrayList<String> answers = q.getAllAnswers();

            for(String ans : answers){
                elements.add(new JRadioButton(ans));
            }
            ButtonGroup group = new ButtonGroup();
            int i = 3;
            gcc.anchor = GridBagConstraints.WEST;
            for(JRadioButton el : elements){
                group.add(el);
                gcc.gridx = 0;
                gcc.gridy = i;
                question.add(el,gcc);
                i++;
            }
            gcc.anchor = GridBagConstraints.CENTER;
            JSeparator sp = new JSeparator();
            gcc.gridx = 0;
            gcc.gridy = 8;

            question.add(sp,gcc);

            sendButton = new JButton("Send answer");
            gcc.gridx = 0;
            gcc.gridy = 9;
            sendButton.setPreferredSize(new Dimension(120,50));
            question.add(sendButton,gcc);

            mm.setQuestion(q);
            mm.setPosition(iterator);
        }else{
            JLabel waiting = new JLabel("Waiting for  the other players");
            question.add(waiting);
        }

        question.setVisible(true);
        TournamentController tc = new TournamentController(frame, question,sendButton,elements,q,name,sender,mm,timeLabel,questions,iterator);


    }

    public JPanel getPanel(){
        return question;
    }
}
