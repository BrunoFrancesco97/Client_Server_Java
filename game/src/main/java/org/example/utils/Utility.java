package org.example.utils;

import org.example.model.Player;
import org.example.model.Question;

import java.io.File;
import java.util.*;

public abstract class Utility {
    public static int randomIDGenerator(int max){
        return ((int)Math.floor(Math.random()*(max+1)));
    }
    public static synchronized void readQuestionsFromFile(String QUESTION_FILE, Player player, int DOMANDE){
        File questionFile = new File(QUESTION_FILE);
        try{
            player.clearQuestions();
            Scanner scan = new Scanner(questionFile);
            ArrayList<Question> quest = new ArrayList<>();
            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split(";");
                Question q = new Question(line[0],line[1]);
                quest.add(q);
            }
            Collections.shuffle(quest);
            List<Question> resized = quest.subList(0,DOMANDE);
            player.questions = new ArrayList<>(resized);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized ArrayList<Player> readQuestionsFromFile(String QUESTION_FILE, ArrayList<Player> players, int DOMANDE){
        File questionFile = new File(QUESTION_FILE);
        ArrayList<Question> questions = new ArrayList<>();
        try{
            Scanner scan = new Scanner(questionFile);
            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split(";");
                Question q = new Question(line[0],line[1],line[2],line[3],line[4],line[5]);
                questions.add(q);
            }
            Collections.shuffle(questions);
            List<Question> resized = questions.subList(0,DOMANDE);
            for(Player p : players){
                p.clearQuestions();
                p.questions = new ArrayList<>(resized);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return players;
    }
}
