package com.example.smsmanagerapp.table.manager.messageout;

import com.example.smsmanagerapp.data.Data;

public interface MessageOutManager {

   public boolean createNewOutMessage(String content, int groupContactId);

   Iterable<? extends Data> getAllMessages();

   void deleteMessage(int id);
}
