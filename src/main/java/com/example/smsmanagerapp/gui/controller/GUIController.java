package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;

public interface GUIController {
    public void updateGUI(Data data);
    public void addMessageManager(MessageManager messageManager);
    public void loadMessages();
    public void testReturn();
    public MessageRecencyType getRecencyType();
}
