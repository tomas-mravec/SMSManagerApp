package com.example.smsmanagerapp.container.interfaces;

import com.example.smsmanagerapp.container.type.MessageContainerType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;

import java.util.List;

public interface MessageContainer {
   public List<? extends Data> getAllMessages();
   public MessageContainerType getContainerType();
   public void addMessage(Data data);

   void remove(Data data);
}
