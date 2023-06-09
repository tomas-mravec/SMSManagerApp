package com.example.smsmanagerapp.container;

import com.example.smsmanagerapp.container.interfaces.MessageContainerObserver;
import com.example.smsmanagerapp.container.type.MessageContainerType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;


import java.util.ArrayList;
import java.util.List;

public class NewSMSMessageContainer implements MessageContainerObserver {
    private List<SMSMessage> smsMessages;
    private MessageContainerType messageContainerType;

    public NewSMSMessageContainer() {
        messageContainerType = MessageContainerType.NEW_MESSAGE;
        smsMessages = new ArrayList<>();
    }
    @Override
    public void newMessage(Data data) {
        handleNewMessage(data);
    }

    private void handleNewMessage(Data data) {
        smsMessages.add((SMSMessage) data);
    }

    @Override
    public List<? extends Data> getAllMessages() {
        return smsMessages;
    }

    @Override
    public MessageContainerType getContainerType() {
        return messageContainerType;
    }

    @Override
    public void addMessage(Data data) {
        smsMessages.add((SMSMessage) data);
    }

    @Override
    public void remove(Data data) {
        smsMessages.remove(data);
    }
}
