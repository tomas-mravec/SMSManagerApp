package com.example.smsmanagerapp.table.manager.messageout;

import com.example.smsmanagerapp.data.Data;

import java.time.LocalDate;
import java.util.Date;

public interface MessageOutManager {

   public boolean createNewOutMessage(String content, int groupContactId);

   Iterable<? extends Data> getAllMessages();

   void deleteMessage(int id);
   public Iterable<? extends Data> filterMessages(String receiverFilter, LocalDate dateFrom, LocalDate dateTo);
}
