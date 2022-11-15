package org.example;

import org.example.utils.Sender;
import org.example.view.WindowView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

//TODO: GESTIRE PRONTO NON PRONTO SU INTERFACCIA FRIENDLY, SE TUTTI I GIOCATORI NEL MATCH SONO READY FAR PARTIRE TIMER START DI TIPO X SECONDI
//TODO: IMPLEMENTARE TIMER MASSIMO PARTITA FRIENDLY
//TODO: IMPLEMENTARE TOURNAMENT MODE
//TODO: IMPLEMENTARE TEST CASES SU SOCKET E VEDERE CHE RISPONDANO QUANTO CI SI ASPETTI


public class Main {
    private static final int PORT = 9000;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", PORT);
            WindowView w = new WindowView(new Sender(socket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}