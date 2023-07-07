package com.example.smsmanagerapp.gui.controller.interfaces;

import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import javafx.fxml.Initializable;

public interface GUIController extends Initializable {
    public void addMessageManager(MessageManager messageManager);
    public void loadMessages();
    public void testReturn();
    public MessageRecencyType getRecencyType();
}
