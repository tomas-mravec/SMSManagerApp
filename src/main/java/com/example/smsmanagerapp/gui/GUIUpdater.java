package com.example.smsmanagerapp.gui;

import com.example.smsmanagerapp.gui.controller.GUIController;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;

public interface GUIUpdater extends IMessageListenerObserver {
    public void addGUIController(GUIController guiController);
}
