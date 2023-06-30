package com.example.smsmanagerapp.listener;

import com.example.smsmanagerapp.interfaces.IMessageListenerObservable;
import com.example.smsmanagerapp.response.ResponseContainer;

public interface MessageListener extends IMessageListenerObservable {

    public void listenForMessages();
    public ResponseContainer getResponseContainer();
}
