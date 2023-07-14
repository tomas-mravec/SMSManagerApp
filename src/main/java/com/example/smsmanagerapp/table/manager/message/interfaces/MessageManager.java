package com.example.smsmanagerapp.table.manager.message.interfaces;

import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;

import java.time.LocalDate;
import java.util.List;

public interface MessageManager {
   public List<? extends Data> getAllMessages();

   public MessageRecencyType getContainerType();
   public List<? extends Data> getAllSeenMessages();
   public List<? extends Data> getAllNewMessages();

   public void setMessageAsSeen(Data data);

    void setMessagesAsSeen(List<Integer> identifiers);

    public List<? extends Data> getFilteredMessages(String filter, LocalDate dateFilter, LocalDate dateFilterTo, boolean seen);

    List<? extends Data> getAllSeenByDate(LocalDate date);

    void remove(Data data);

    void deleteMessagesById(List<Integer> integers);
}
