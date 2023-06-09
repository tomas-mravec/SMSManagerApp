package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.interfaces.MessageContainer;
import com.example.smsmanagerapp.data.Data;

public interface GUIController {
    public void updateGUI(Data data);
    public void addMessageContainer(MessageContainer messageContainer);
    public void loadMessages();
    public void testReturn();
}
