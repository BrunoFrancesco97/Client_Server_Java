package org.example.utils;

import com.google.gson.Gson;
import org.example.interfaces.Senders;
import org.example.model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SenderClient<K> implements Senders<K> {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public SenderClient(ObjectInputStream in, ObjectOutputStream out){
        this.in = in;
        this.out = out;
    }
    @Override
    public void sendToClient(Message m, String event) {
        try{
            Message mex = new Message<>(m.getOwner(),event.toUpperCase());
            System.out.println("Exiting: "+mex);
            this.out.writeObject(mex);
            this.out.flush();
            this.out.reset();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendToClient(Message m, String event, K element) {
        try{
            Message mex = new Message<>(m.getOwner(),event.toUpperCase(),element);
            System.out.println("Exiting: "+mex);
            this.out.writeObject(mex);
            this.out.flush();
            this.out.reset();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
