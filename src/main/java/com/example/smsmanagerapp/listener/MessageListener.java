package com.example.smsmanagerapp.listener;

import com.example.smsmanagerapp.interfaces.IMessageListenerObservable;

public interface MessageListener extends IMessageListenerObservable {

    public void listenForMessages();
}
