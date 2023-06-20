package com.example.smsmanagerapp.container.message.interfaces;

import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;

import java.time.LocalDate;
import java.util.List;

public interface MessageManager {
   public List<? extends Data> getAllMessages();

   public MessageRecencyType getContainerType();
   public List<? extends Data> getAllSeenMessages();
   public List<? extends Data> getAllNewMessages();

   public void setMessageAsSeen(Data data);
   public List<? extends Data> getFilteredMessages(String filter, LocalDate dateFilter);

    List<? extends Data> getAllSeenByDate(LocalDate date);

    void remove(Data data);
}
