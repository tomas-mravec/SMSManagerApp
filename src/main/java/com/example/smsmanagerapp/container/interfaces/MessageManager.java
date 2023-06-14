package com.example.smsmanagerapp.container.interfaces;

import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;

import java.util.List;

public interface MessageManager {
   public List<? extends Data> getAllMessages();
   public MessageRecencyType getContainerType();
   public void addMessage(Data data);
   public List<? extends Data> getAllSeenMessages();
   public List<? extends Data> getAllNewMessages();

   public void setMessageAsSeen(Data data);

   void remove(Data data);
}
