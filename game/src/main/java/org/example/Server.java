package org.example;

import org.example.model.*;
import org.example.utils.SenderClient;
import org.example.utils.Utility;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Server extends Thread{
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

    private Object lock;
    private boolean flag;
    public Server(Socket s, Memory<Match> matchesSaved, Memory<Match> matchesList, Memory<Player> usersConnected, Object lock){
        this.s = s;
        this.match = null;
        this.player = new Player();
        this.flag = false;
        this.matchesSaved = matchesSaved;
        this.matchesList = matchesList;
        this.usersConnected = usersConnected;
        this.lock = lock;
        try{
            this.in = new ObjectInputStream(this.s.getInputStream());
            this.out = new ObjectOutputStream(this.s.getOutputStream());
            this.senderClient = new SenderClient(this.in, this.out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void run() {
        this.read(this.lock);
    }

    private void read(Object lock){
        try{
            while (!flag){
                Message mex = (Message) this.in.readObject();
                System.out.println("Entering : "+mex);
                if(mex != null){ //TODO: CHECK IF MEX IS A REAL MESSAGE
                    switch (mex.getEvent()){
                        case "NAME":
                            this.player.setName(mex.getOwner());
                            if(this.usersConnected.checkElement(this.player)){
                                this.senderClient.sendToClient(mex,"mode","no");
                            }else{
                                this.usersConnected.add(this.player);
                                this.match = this.checkPreviousMatches();
                                if(this.match != null){ //HO UN MATCH NON FINITO DA POTER FINIRE
                                    this.senderClient.sendToClient(mex,"mode",this.match);
                                }else{
                                    this.senderClient.sendToClient(mex,"mode");
                                }
                            }
                            break;
                        case "START":
                            handleStartGame(mex);
                            break;
                        case "GAME":
                            handleGaming(mex);
                            break;
                        case "END":
                            handleClosing(mex, lock);
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
                        case "UPDATE_NEXT":
                            handleUpdateNext(mex);
                            break;
                        case "RETURN_RANK":
                            handleReturnRank(mex);
                            break;
                        case "COUNT_TOURNAMENT":
                            handleCountTournament(mex);
                    }
                }
            }
        }
        catch (Exception ee){
            ee.printStackTrace();
        }
    }

    private void handleCountTournament(Message mex){
        this.match.removePlayer(this.player);
        if(this.match.getPlayers().size() <= 0){
            this.matchesList.remove(this.match);
            this.match = null;
        }
    }


    private void handleReturnRank(Message mex){
        if(this.match != null){
            ArrayList<QuestionOwner> qo = new ArrayList<>();
            ArrayList<QuestionOwner> questions = new ArrayList<>();
            ArrayList<QuestionOwner> totalQuestions = new ArrayList<>();
            ArrayList<Rank> ranks = new ArrayList<>();
            for(int i = 0; i < this.player.score.questions.size(); i++){
                questions.clear();
                for(Player p : this.match.getPlayers()){ //Get all i-th questions of all players of the match
                    questions.add(new QuestionOwner(p.score.getQuestion(i),p.name));
                    totalQuestions.add(new QuestionOwner(p.score.getQuestion(i),p.name));
                }
                //questions contains the i-th answers given by people
                QuestionOwner min = null;
                boolean found = false;
                //Finding the minimum time answers between the ones given by players
                for(QuestionOwner q : questions){
                    if(!q.getQuest().correct){ //Aggiungo
                        boolean checker = false;
                        for(Rank rr : ranks){
                            if(rr.name.equals(q.getP())){
                                checker = true;
                                break;
                            }
                        }
                        if(!checker){
                            Rank r = new Rank(q.getP(),0);
                            ranks.add(r);
                        }
                    }else{
                        boolean checker = false;
                        for(Rank rr : ranks){
                            if(rr.name.equals(q.getP())){
                                rr.addHalf();
                                checker = true;
                                break;
                            }
                        }
                        if(!checker){
                            Rank r = new Rank(q.getP(),0.5f);
                            ranks.add(r);
                        }
                        if(q.getQuest().correct && !found){
                            min = q;
                            found = true;
                        }
                        if(found && q.getQuest().correct && q.getQuest().seconds < min.getQuest().seconds){
                            min = q;
                        }
                    }
                }
                if(min != null)
                    qo.add(min);
            }
            //Adding 1 points to all min answers
            for(QuestionOwner q : qo){
                boolean checker = false;
                for(Rank r : ranks){
                    if(q.getP().equals(r.name)){
                        checker = true;
                        r.addHalf();
                        break;
                    }
                }
                if(!checker){
                    Rank r = new Rank(q.getP(),1);
                    ranks.add(r);
                }
            }
            boolean checker = false;
            Collections.sort(ranks);
            this.senderClient.sendToClient(mex,"RETURN_RANK",ranks);

        }
    }

    private void handleUpdateReady(Message mex){
        if(mex.getMessage() != null){
            this.player.setReady((Boolean) mex.getMessage());
        }
    }


    private void handleIsEnd(Message mex){
        boolean flag = false;
        if(this.match != null){
            for(Player p : this.match.getPlayers()){
                if((p.hasQuestion() || !p.isHasFinished())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                ArrayList<Score> scores = new ArrayList<>();
                for(Player p : this.match.getPlayers()){
                    scores.add(p.score);
                }
                synchronized (lock){
                    this.matchesList.remove(this.match);
                }
                this.match = null;
                this.senderClient.sendToClient(mex,"IS_END",scores);
            }else{
                this.senderClient.sendToClient(mex,"IS_END");
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
        Match mm = new Match(null,nameGot,null,1);
        Match m;
        synchronized (lock){
            m = this.matchesList.get(mm);
        }
        if(m != null){
            this.match = null;
            m.removePlayer(this.player);
        }
    }

    private void handleUpdatePlayers(Message mex){
        if(this.match != null){
            Match m;
            synchronized (lock){
                m = this.matchesList.get(this.match);
            }
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
            Match mm = new Match(null,nameGot,null,1);
            Match m;
            synchronized (lock){
                m = this.matchesList.get(mm);
            }
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
        this.player.setHasFinished(true);
        this.player.clearQuestions();
        this.senderClient.sendToClient(mex,"end_timer");


    }


    public void handleUpdateNext(Message mex){
        Match m;
        boolean flag = false;
        synchronized (lock){
            m = this.matchesList.get(this.match);
        }
        if(m != null){
            Question q = (Question) mex.getMessage();
            if(!this.player.score.containQuestion(q)){
                this.player.score.addQuestion(q);
            }
            for(Player p : m.getPlayers()){
                if(!p.score.containQuestion(q)){ //Not all players have answered to the question
                    flag = true;
                    break;
                }
            }
            if(flag)
                this.senderClient.sendToClient(mex,"UPDATE_NEXT","no");
            else this.senderClient.sendToClient(mex,"UPDATE_NEXT","ok");
        }
    }
    /**
     * Metodo usato per la gestione di un match di tipo practice o friendly
     * */
    private void handleGaming(Message mex){
        if(!this.match.getType().equals("tournament"))
            player.score.addQuestion((Question) mex.getMessage());
        if(player.hasQuestion()){
            player.setHasFinished(false);
            Question q;
            if(!this.match.getType().equals("tournament"))
                q = player.pickQuestion();
            else q = player.popQuestion();
            if(q != null){
                this.senderClient.sendToClient(mex,"game",q);
            }
        }else{
            player.score.setCompleted(true);
            switch (this.match.getType()){
                case "practice":
                    this.senderClient.sendToClient(mex,"end",player.score);
                    this.player.score.questions.clear();
                    synchronized (lock){
                        this.matchesList.remove(this.match);
                    }
                    this.match = null;
                    break;
                default:
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
            switch(mex.getMessage().toString().toLowerCase()){
                case "practice":
                    this.match = new Match("practice", this.player);
                    this.match.addPlayer(this.player);
                    Utility.readQuestionsFromFile(QUESTION_FILE, this.player, this.match.getNumberQuestions());
                    synchronized (lock){
                        this.matchesList.add(this.match);
                    }
                    Question q = player.pickQuestion();
                    if(q != null){
                        this.senderClient.sendToClient(mex,"game",q);
                    }
                    break;
                case "friendlytournament":
                    ArrayList<Match> matches;
                    synchronized (lock){
                        matches = this.matchesList.clone();
                    }
                    ArrayList<Match> available = new ArrayList<>();
                    for(Match m : matches){
                        if(m.isAvailable() && (m.getType().equals("friendly") || m.getType().equals("tournament")))
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
            Match m = new Match(null, (String) mex.getMessage(),null,1);
            synchronized (lock){
                this.match = this.matchesList.get(m);
            }
            if(this.match != null){
                this.match.setAvailable(false);
                ArrayList<Player> playersQuestions = Utility.readQuestionsFromFile(QUESTION_FILE, this.match.getPlayers(),this.match.getNumberQuestions());
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
                boolean result = matchesList.checkElement(new Match(null,nameMatch, this.player,1));
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
                    synchronized (lock){
                        this.matchesList.add(this.match);
                    }
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
    private void handleClosing(Message mex, Object lock){
        System.out.println("Closing the connection");
        if(mex.getMessage() != null && this.match != null && this.match.getType().equals("practice")){//It happens only if I'm in practice and I haven't ended the match
            MatchChecker mm = (MatchChecker) mex.getMessage();
            this.match.getPlayer(mex.getOwner()).questions.add(mm.getQuestion());
            this.match.getPlayer(mex.getOwner()).setIndexLastQuestion(mm.getPosition());
            synchronized (lock){
                this.matchesList.remove(this.match);
            }
            matchesSaved.add(this.match);//TODO: E SE IL MATCH FOSSE FRIENDLY O TOURNAMENT COME LO TRATTO?
        }else{
            if(this.match != null && this.match.getType().equals("practice")){ //It should not enter here since match couldn't be evaluated to something and at the same thing not entering on the previous branch of the if
                synchronized (lock){
                    this.matchesList.remove(this.match);
                }
            }
        }
        boolean hostDeleted = false;
        synchronized (lock){
            for(Iterator<Match> it = this.matchesList.getMemory().iterator();it.hasNext();){
                Match m = it.next();
                if((m.getType().equals("friendly") || m.getType().equals("tournament")) && m.containsUser(this.player)){
                    if(this.player.equals(m.getHost())){
                        hostDeleted = true;
                    }
                    m.removePlayer(this.player);
                    if(m.getPlayers().size() < 1){
                        it.remove();
                    }else{
                        if(hostDeleted){
                            m.setHost(m.getFirstPLayer());
                            break;
                        }
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
     * Metodo usato per la creazione di match di tipo friendly or tournament
     * */
    private void handleCreate(Message mex){
        try{
            if(mex.getMessage() instanceof String){
                String[] splitted = ((String) mex.getMessage()).split(":");
                String name = splitted[0];
                int size = Integer.parseInt(splitted[1]);
                int time = Integer.parseInt(splitted[2]);
                int nQuestions = Integer.parseInt(splitted[3]);
                String type = splitted[4];
                this.match = new Match(type, name, this.player, size,time,nQuestions);
                this.player.setReady(false);
                this.match.addPlayer(this.player);
                this.match.setAvailable(true);
                synchronized (lock){
                    this.matchesList.add(this.match);
                }
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
        synchronized (lock){
            this.matchesList.remove(this.match);
        }
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
