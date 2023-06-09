package com.example.smsmanagerapp.container;

import com.example.smsmanagerapp.container.interfaces.MessageContainer;
import com.example.smsmanagerapp.container.type.MessageContainerType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;

import java.util.ArrayList;
import java.util.List;

public class HistorySMSMessageContainer implements MessageContainer {

    private List<SMSMessage> smsMessages;
    private MessageContainerType messageContainerType;

    public HistorySMSMessageContainer() {
        messageContainerType = MessageContainerType.HISTORY_MESSAGE;
        smsMessages = new ArrayList<>();
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

    }
}
