package org.example.utils;

import com.google.gson.Gson;
import org.example.model.Message;

import java.io.*;
import java.net.Socket;

public class Sender {
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Socket socket;

    public Sender(Socket socket){
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Message sendAndRead(Message message){
        Object result;
        try{
            this.out.writeObject(message);
            this.out.flush();
            this.out.reset();
            result = this.in.readObject();
            Message result2;
            if(result != null){
                result2 = (Message) result;
                System.out.println("Incoming message: "+result2);
                return result2;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void send(Message message){
        try{
            this.out.writeObject(message);
            this.out.flush();
            this.out.reset();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void close(){
        System.out.println("Closing the connection with the server!");
        try {
            this.socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
