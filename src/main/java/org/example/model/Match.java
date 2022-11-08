package org.example.model;

import org.example.utils.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Match implements Serializable {
    public ArrayList<Player> players;
    public boolean available; //Tells if the match can be joined by other users
    public String type; //Practice/Tournament/Friendly
    public int id;
    public String name;

    public Player host;
    private final int SIZE = 4;
    public Match(){
        this.players = new ArrayList<>();
        this.available = false ;
        this.type = null;
        this.id = Utility.randomIDGenerator(10000);
        this.name = ""+this.id;
        this.host = null;
    }
    public Match(String type, Player host){
        this.players = new ArrayList<>();
        this.available = false ;
        this.type = type;
        this.id = Utility.randomIDGenerator(10000);
        this.name = ""+this.id;
        this.host = host;
    }
    public Match(String type, String name, Player host){
        this.players = new ArrayList<>();
        this.available = false ;
        this.type = type;
        this.id = Utility.randomIDGenerator(10000);
        this.name = name;
        this.host = host;
    }
    public synchronized void addPlayer(Player p){
        if(players.size() < SIZE)
            players.add(p);
    }

    public synchronized  void removePlayer(Player p){
        if(players.size() > 0)
            players.remove(p);
    }
    public synchronized String returnListOfPlayers(){
        String list = "";
        for(Player el : players){
            list = list+"\n"+el.toString();
        }
        return list;
    }

    public synchronized boolean containsUser(Player p){
        for(Player pp : players){
            if(pp.equals(p))
                return true;
        }
        return false;
    }

    public synchronized Player getPlayer(Player p){
        for(Player pp : players){
            if(pp.equals(p))
                return pp;
        }
        return null;
    }

    public synchronized Player getPlayer(String p){
        for(Player pp : players){
            if(pp.name.equals(p))
                return pp;
        }
        return null;
    }
    @Override
    public String toString() {
        return "Match{" +
                "players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return (id == match.id) || (name.equals(match.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
