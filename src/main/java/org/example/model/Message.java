package org.example.model;

import org.example.interfaces.Messages;

import java.io.Serializable;

public class Message<K> implements Messages<String,K,String>, Serializable {
    private static final long serialVersionUID = 1L;
    private String owner;
    private String event;
    private K message;
    public Message(String owner){
        this.owner = owner;
        this.event = "QUIT";
        this.message = null;
    }
    public Message(String owner, String event){
        this.owner = owner;
        this.event = event;
        this.message = null;
    }
    public Message(String owner, String event, K message){
        this.owner = owner;
        this.event = event;
        this.message = message;
    }
    public String getOwner(){
        return this.owner;
    }

    public String getEvent(){
        return this.event;
    }

    @Override
    public K getMessage() {
        return this.message;
    }

    public void setEvent(String event){
        this.event = event;
    }

    @Override
    public void setMessage(K message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "owner='" + owner + '\'' +
                ", event='" + event + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
