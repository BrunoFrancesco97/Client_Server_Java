package org.example.interfaces;

public interface Messages<K,Y,Z> {
    public Z getOwner();
    public K getEvent();
    public Y getMessage();
    public void setEvent(K event);
    public void setMessage(Y message);

}
