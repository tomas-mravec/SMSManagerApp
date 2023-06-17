package com.example.smsmanagerapp.garbage;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;

import java.util.ArrayList;
import java.util.List;

public class HistorySMSMessageManager  {

    private List<SMSMessage> smsMessages;
    private MessageRecencyType messageRecencyType;

    public HistorySMSMessageManager() {
        messageRecencyType = MessageRecencyType.HISTORY_MESSAGE;
        smsMessages = new ArrayList<>();
    }

    public List<? extends Data> getAllMessages() {
        return smsMessages;
    }

    public MessageRecencyType getContainerType() {
        return messageRecencyType;
    }


    public List<? extends Data> getAllSeenMessages() {
        return null;
    }

    public List<? extends Data> getAllNewMessages() {
        return null;
    }

    public void setMessageAsSeen(Data data) {

    }

    public List<? extends Data> getFilteredMessages(String filter) {
        return null;
    }

    public void remove(Data data) {

    }
}
