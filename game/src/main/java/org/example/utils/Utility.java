package org.example.utils;

import org.example.model.Player;
import org.example.model.Question;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public abstract class Utility {
    public static int randomIDGenerator(int max){
        return ((int)Math.floor(Math.random()*(max+1)));
    }
    public static synchronized void readQuestionsFromFile(String QUESTION_FILE, Player player, int DOMANDE){
        File questionFile = new File(QUESTION_FILE);
        try{
            player.clearQuestions();
            Scanner scan = new Scanner(questionFile);
            int i = 0;
            while(scan.hasNextLine() && i < DOMANDE){
                String[] line = scan.nextLine().split("\\?");
                Question q = new Question(line[0],line[1]);
                player.addQuestion(q);
                i++;
            }
            Collections.shuffle(player.questions);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized ArrayList<Player> readQuestionsFromFile(String QUESTION_FILE, ArrayList<Player> players, int DOMANDE){
        File questionFile = new File(QUESTION_FILE);
        ArrayList<Question> questions = new ArrayList<>();
        try{
            Scanner scan = new Scanner(questionFile);
            int i = 0;
            while(scan.hasNextLine() && i < DOMANDE){
                String[] line = scan.nextLine().split("\\?");
                Question q = new Question(line[0],line[1]);
                questions.add(q);
                i++;
            }
            Collections.shuffle(questions);
            for(Player p : players){
                p.clearQuestions();
                p.questions = new ArrayList<>(questions);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return players;
    }
}
