package org.example;

import org.example.model.Match;
import org.example.model.Memory;
import org.example.model.Player;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    private static final int PORT = 9000;
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            //Memory<Socket> usersConnected = new Memory<>();
            Memory<Match> matchesSaved = new Memory<>();
            Memory<Match> matchesList = new Memory<>();
            Memory<Player> onlinePLayers = new Memory<>();
            Object memoryLock = new Object();
            System.out.println("SERVER LISTENING AT PORT "+PORT);
            while(true){
                Socket socketClient = serverSocket.accept();
                System.out.println("CONNECTION ESTABLISHED");
                Server s = new Server(socketClient,matchesSaved, matchesList,onlinePLayers,memoryLock);
                s.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}