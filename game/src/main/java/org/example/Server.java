package org.example;

import com.google.gson.Gson;
import org.example.model.*;
import org.example.utils.SenderClient;
import org.example.utils.Utility;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread{
    private static final int DOMANDE = 2; //TODO: FARLO DEFINIRE DA UTENTE O LASCIARLO DI DEFAULT
    private static final String QUESTION_FILE = "./questions.txt";
    private final Socket s;
    private Match match;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;
    private Memory<Match> matchesSaved;
    private Memory<Match> matchesList;
    private Memory<Player> usersConnected;

    private SenderClient senderClient;
    private boolean flag;
    public Server(Socket s, Memory<Match> matchesSaved, Memory<Match> matchesList, Memory<Player> usersConnected){
        this.s = s;
        this.match = null;
        this.player = new Player();
        this.flag = false;
        this.matchesSaved = matchesSaved;
        this.matchesList = matchesList;
        this.usersConnected = usersConnected;
        try{
            this.in = new ObjectInputStream(this.s.getInputStream());
            this.out = new ObjectOutputStream(this.s.getOutputStream());
            this.senderClient = new SenderClient(this.in, this.out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void run() {
        this.read();
    }

    private void read(){
        try{
            while (!flag){
                Message mex = (Message) this.in.readObject();
                if(mex != null){ //TODO: CHECK IF MEX IS A REAL MESSAGE
                    System.out.println("Client entered: "+mex);
                    switch (mex.getEvent()){
                        case "NAME":
                            this.player.setName(mex.getOwner());
                            this.usersConnected.add(this.player);
                            this.match = this.checkPreviousMatches();
                            if(this.match != null){ //HO UN MATCH NON FINITO DA POTER FINIRE
                                this.senderClient.sendToClient(mex,"mode",this.match.getType());
                            }else{
                                this.senderClient.sendToClient(mex,"mode");
                            }
                            break;
                        case "START":
                            handleStartGame(mex);
                            break;
                        case "GAME":
                            handleGaming(mex);
                            break;
                        case "END":
                            handleClosing(mex);
                            break;
                        case "END_TIMER":
                            handleEndTimer(mex);
                            break;
                        case "REMOVE":
                            handleRemove(mex);
                            break;
                        case "RESUME":
                            handleContinueGame(mex);
                            break;
                        case "NAME_CHECKER":
                            handleNameChecker(mex);
                            break;
                        case "CREATE":
                            handleCreate(mex);
                            break;
                        case "MATCH_REMOVER":
                            handleRemover(mex);
                            break;
                        case "FRIENDLY_START":
                            handleFriendlyStartGame(mex);
                            break;
                        case "GET_IN":
                            handleGetIn(mex);
                            break;
                        case "UPDATE_PLAYERS":
                            handleUpdatePlayers(mex);
                            break;
                        case "REMOVE_PLAYER":
                            handleRemovePlayer(mex);
                            break;
                        case "DROP_QUESTION":
                            handleDropQuestion(mex);
                            break;
                        case "IS_END":
                            handleIsEnd(mex);
                            break;
                        case "UPDATE_READY":
                            handleUpdateReady(mex);
                            break;
                    }
                }
            }
        }
        catch (Exception ee){
            ee.printStackTrace();
        }
    }

    private void handleUpdateReady(Message mex){
        if(mex.getMessage() != null){
            this.player.setReady((Boolean) mex.getMessage());
        }
    }


    private void handleIsEnd(Message mex){
        String result = "N";
        if(this.match != null){
            for(Player p : this.match.getPlayers()){
                System.out.println(p + " "+(p.hasQuestion() && !p.isHasFinished()));
                System.out.println(p.hasQuestion());
                System.out.println(!p.isHasFinished());
                if((p.hasQuestion() && !p.isHasFinished())){
                    System.out.println(p);
                    result = "Y";
                    break;
                }
            }
            if(result.equals("N")){
                ArrayList<Score> scores = new ArrayList<>();
                for(Player p : this.match.getPlayers()){
                    scores.add(p.score);
                }
                this.matchesList.remove(this.match);
                this.match = null;
                this.senderClient.sendToClient(mex,"IS_END",scores);
            }else{
                this.senderClient.sendToClient(mex,"IS_END","Y");
            }
        }

    }

    private void handleDropQuestion(Message mex){
        if(mex.getMessage() != null && mex.getMessage() instanceof String && this.match != null){
            Question q = null;
            for(Player p : this.match.getPlayers()){
                if(p.name.equals(mex.getOwner())){
                    q = p.popQuestion();
                    break;
                }
            }
            this.senderClient.sendToClient(mex,"DROP_QUESTION",q);
            //}
        }
    }
    private void handleRemovePlayer(Message mex){
        String nameGot = (String) mex.getMessage();
        Match mm = new Match("friendly",nameGot,null,1);
        Match m = this.matchesList.get(mm);
        if(m != null){
            this.match = null;
            m.removePlayer(this.player);
        }
    }

    private void handleUpdatePlayers(Message mex){
        if(this.match != null){
            Match m = this.matchesList.get(this.match);
            if(m != null){
                this.senderClient.sendToClient(mex,"UPDATE_PLAYERS",this.match);
            }else{
                this.match = null;
                this.senderClient.sendToClient(mex,"UPDATE_PLAYERS",new Match());
            }
        }else{
            this.senderClient.sendToClient(mex,"UPDATE_PLAYERS",new Match());
        }
    }
    private void handleGetIn(Message mex){
        if(mex.getMessage() != null && mex.getMessage() instanceof String){
            String nameGot = (String) mex.getMessage();
            Match mm = new Match("friendly",nameGot,null,1);
            Match m = this.matchesList.get(mm);
            if(m != null && m.getPlayers().size() < m.getSize() && m.isAvailable()){
                this.match = m;
                this.player.setReady(false);
                this.match.addPlayer(this.player);
                this.senderClient.sendToClient(mex,"GET_IN",this.match);
            }else{
                this.senderClient.sendToClient(mex,"GET_IN");
            }
        }
    }

    public void handleEndTimer(Message mex){
        this.senderClient.sendToClient(mex,"end_timer",player.score);
        player.setHasFinished(true);

    }

    /**
     * Metodo usato per la gestione di un match di tipo practice
     * */
    private void handleGaming(Message mex){
        player.score.addQuestion((Question) mex.getMessage());
        if(player.hasQuestion()){
            player.setHasFinished(false);
            Question q = player.pickQuestion();
            if(q != null){
                System.out.println(player.score);
                this.senderClient.sendToClient(mex,"game",q);
            }
        }else{
            switch (this.match.getType()){
                case "practice":
                    player.score.setCompleted(true);
                    this.senderClient.sendToClient(mex,"end",player.score);
                    this.player.score.questions.clear();
                    this.matchesList.remove(this.match);
                    this.match = null;
                    break;
                case "friendly":
                    player.score.setCompleted(true);
                    this.senderClient.sendToClient(mex,"end",player.score);
                    break;
            }
            player.setHasFinished(true);
        }
    }

    /**
     * Metodo usato per la gestione della scelta del tipo di match da interfaccia ModeView
     * */
    private void handleStartGame(Message mex){
        try{
            Utility.readQuestionsFromFile(QUESTION_FILE, this.player, DOMANDE);
            switch(mex.getMessage().toString().toLowerCase()){
                case "practice":
                    this.match = new Match("practice", this.player);
                    this.match.addPlayer(this.player);
                    this.matchesList.add(this.match);
                    Question q = player.pickQuestion();
                    if(q != null){
                        this.senderClient.sendToClient(mex,"game",q);
                    }
                    break;
                case "friendly":
                    ArrayList<Match> matches = this.matchesList.clone();
                    ArrayList<Match> available = new ArrayList<>();
                    for(Match m : matches){
                        if(m.isAvailable() && m.getType().equals("friendly"))
                            available.add(m);
                    }
                    this.senderClient.sendToClient(mex,"list",available);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void handleFriendlyStartGame(Message mex){
        if(mex.getMessage() != null && mex.getMessage() instanceof String){
            Match m = new Match("friendly", (String) mex.getMessage(),null,1);
            this.match = this.matchesList.get(m);
            if(this.match != null){
                this.match.setAvailable(false);
                ArrayList<Player> playersQuestions = Utility.readQuestionsFromFile(QUESTION_FILE, this.match.getPlayers(),DOMANDE);
                this.match.setPlayers(new ArrayList<>(playersQuestions));
                for(Player p : this.match.getPlayers()){
                    p.score.questions.clear();
                    p.score.setCompleted(false);
                    p.setHasFinished(false);
                    p.setReady(false);
                }
            }
        }
    }


    private void handleNameChecker(Message mex){
        try{
            String nameMatch = (String) mex.getMessage();
            if(nameMatch != null && nameMatch.length() > 0){
                Match m = new Match("friendly",nameMatch, this.player,1);
                boolean result = matchesList.checkElement(m);
                if(result){
                    this.senderClient.sendToClient(mex,"NAME_CHECKER","Y");
                }else{
                    this.senderClient.sendToClient(mex,"NAME_CHECKER");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void handleContinueGame(Message mex){
        try{
            switch(mex.getMessage().toString().toLowerCase()){
                case "practice":
                    this.matchesSaved.remove(this.match);
                    this.player.score = this.match.getPlayer(mex.getOwner()).score;
                    this.matchesList.add(this.match);
                    this.player.questions = this.getPreviousQuestions(this.player.name, this.match);
                    Question q = this.player.pickQuestion();
                    if(q != null){
                        this.senderClient.sendToClient(mex,"game",q);
                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Metodo usato per la rimozione di un match salvato e non terminato
     * */
    private void handleRemove(Message mex){
        this.matchesSaved.remove(this.match);
        this.match = null;
    }

    /**
     * Metodo usato per gestire la chiusura di una connessione con client
     * */
    private void handleClosing(Message mex){
        System.out.println("Closing the connection");
        if(mex.getMessage() != null && this.match != null && this.match.getType().equals("practice")){//It happens only if I'm in practice and I haven't ended the match
            MatchChecker mm = (MatchChecker) mex.getMessage();
            this.match.getPlayer(mex.getOwner()).questions.add(mm.getQuestion());
            this.matchesList.remove(this.match);
            matchesSaved.add(this.match);//TODO: E SE IL MATCH FOSSE FRIENDLY O TOURNAMENT COME LO TRATTO?
        }else{
            if(this.match != null && this.match.getType().equals("practice")){ //It should not enter here since match couldn't be evaluated to something and at the same thing not entering on the previous branch of the if
                this.matchesList.remove(this.match);
            }
        }
        boolean hostDeleted = false;
        for(Match m : this.matchesList.getMemory()){
            if(m.getType().equals("friendly") && m.containsUser(this.player)){
                if(this.player.equals(m.getHost())){
                    hostDeleted = true;
                }
                m.removePlayer(this.player);
                if(m.getPlayers().size() < 1){
                    this.matchesList.remove(m);
                }else{
                    if(hostDeleted){
                        m.setHost(m.getFirstPLayer());
                        break;
                    }
                }
            }
        }
        try{
            this.s.close();
            this.in.close();
            this.out.close();
            this.flag = true;
            this.usersConnected.remove(this.player);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo usato per la creazione di match di tipo friendly
     * */
    private void handleCreate(Message mex){
        try{
            if(mex.getMessage() instanceof String){
                String name = ((String) mex.getMessage()).substring(0,((String) mex.getMessage()).length()-1);
                int size = Character.getNumericValue(((String)mex.getMessage()).charAt(((String) mex.getMessage()).length()-1));
                this.match = new Match("friendly", name, this.player, size);
                this.player.setReady(false);
                this.match.addPlayer(this.player);
                this.match.setAvailable(true);
                this.matchesList.add(this.match);
                this.senderClient.sendToClient(mex,"create",this.match);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo usato per la rimozione di un match in corso o in attesa di essere avviato
     * */
    private void handleRemover(Message mex){
        this.matchesList.remove(this.match);
        this.match = null;
        this.senderClient.sendToClient(mex,"MATCH_REMOVER","ok");
    }

    /**
     * Metodo usato per controllare se ci sono match in precedenza non terminati del mio player
     * */
    private synchronized Match checkPreviousMatches(){
        for(Match m : this.matchesSaved.getMemory()){
            if((m.getType().equals("practice")) && m.containsUser(this.player)){
                return m;
            }
        }
        return null;
    }

    private ArrayList<Question> getPreviousQuestions(String name, Match m){
        Player p = m.getPlayer(name);
        if(p != null){
            return p.questions;
        }
        return null;
    }
}
