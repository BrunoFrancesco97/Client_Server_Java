package org.example.utils;

import org.example.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public Message send(Message message){
        Message result = null;
        try{
            this.out.writeObject(message);
            this.out.flush();
            this.out.reset();
            result = (Message) this.in.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
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
