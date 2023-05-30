package com.example.smsmanagerapp.interfaces;

public interface IMessageListenerObservable {
    public void addObserver(IMessageListenerObserver listenerObserver);
    public void deleteObserver(IMessageListenerObserver listenerObserver);
    public void notifyObserver();
}
