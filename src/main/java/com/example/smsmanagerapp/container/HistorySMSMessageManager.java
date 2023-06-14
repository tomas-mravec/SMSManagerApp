package com.example.smsmanagerapp.container;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;

import java.util.ArrayList;
import java.util.List;

public class HistorySMSMessageManager implements MessageManager {

    private List<SMSMessage> smsMessages;
    private MessageRecencyType messageRecencyType;

    public HistorySMSMessageManager() {
        messageRecencyType = MessageRecencyType.HISTORY_MESSAGE;
        smsMessages = new ArrayList<>();
    }
    @Override
    public List<? extends Data> getAllMessages() {
        return smsMessages;
    }

    @Override
    public MessageRecencyType getContainerType() {
        return messageRecencyType;
    }

    @Override
    public void addMessage(Data data) {
        smsMessages.add((SMSMessage) data);
    }

    @Override
    public List<? extends Data> getAllSeenMessages() {
        return null;
    }

    @Override
    public List<? extends Data> getAllNewMessages() {
        return null;
    }

    @Override
    public void setMessageAsSeen(Data data) {

    }

    @Override
    public void remove(Data data) {

    }
}
