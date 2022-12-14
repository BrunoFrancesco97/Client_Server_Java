package org.example;

import org.example.utils.Sender;
import org.example.view.WindowView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;



public class Main {
    private static final int PORT = 9002;

    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", PORT);
            WindowView w = new WindowView(new Sender(socket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}