package org.example.interfaces;

import org.example.model.Message;

public interface Senders<K> {
    public void sendToClient(Message m, String event);
    public void sendToClient(Message m, String event, K element);
}
