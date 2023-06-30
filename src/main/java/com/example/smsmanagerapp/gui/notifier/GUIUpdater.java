package com.example.smsmanagerapp.gui.notifier;

import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;

public interface GUIUpdater extends IMessageListenerObserver {
    public void addGUIController(GUIControllerUpdateable guiController);
}
