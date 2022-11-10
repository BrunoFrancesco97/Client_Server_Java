package org.example.model;

import org.example.utils.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Match implements Serializable {
    private ArrayList<Player> players;
    private boolean available; //Tells if the match can be join by other users
    private String type; //Practice/Tournament/Friendly
    public int id;
    private String name;

    private Player host;
    private int size;
    public Match(){
        this.players = new ArrayList<>();
        this.available = false ;
        this.type = null;
        this.id = Utility.randomIDGenerator(10000);
        this.name = ""+this.id;
        this.host = null;
        this.size = 0;
    }
    public Match(String type, Player host){
        this.players = new ArrayList<>();
        this.available = false ;
        this.type = type;
        this.id = Utility.randomIDGenerator(10000);
        this.name = ""+this.id;
        this.host = host;
        this.size = 4; //DEFAULT SIZE
    }
    public Match(String type, String name, Player host, int size){
        this.players = new ArrayList<>();
        this.available = false ;
        this.type = type;
        this.id = Utility.randomIDGenerator(10000);
        this.name = name;
        this.host = host;
        this.size = size;
    }

    public synchronized int getSize() {
        return size;
    }

    public synchronized ArrayList<Player> getPlayers() {
        return players;
    }

    public synchronized void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public synchronized boolean isAvailable() {
        return available;
    }

    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }

    public synchronized String getType() {
        return type;
    }

    public synchronized void setType(String type) {
        this.type = type;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized Player getHost() {
        return host;
    }

    public synchronized void setHost(Player host) {
        this.host = host;
    }

    public synchronized void addPlayer(Player p){
        if(players.size() < this.size)
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
