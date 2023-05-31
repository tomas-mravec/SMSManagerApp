package com.example.smsmanagerapp.interfaces;

import com.example.smsmanagerapp.data.Data;

public interface IMessageListenerObservable {
    public void addObserver(IMessageListenerObserver listenerObserver);
    public void deleteObserver(IMessageListenerObserver listenerObserver);
    public void notifyObservers(Data data);
}
