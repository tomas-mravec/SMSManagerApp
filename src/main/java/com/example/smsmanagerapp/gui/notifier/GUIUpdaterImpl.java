package com.example.smsmanagerapp.gui.notifier;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;

import java.util.ArrayList;
import java.util.List;

public class GUIUpdaterImpl implements GUIUpdater{

    private List<GUIControllerUpdateable> guiControllers;

    public GUIUpdaterImpl() {
        this.guiControllers = new ArrayList<>();
    }

    public void addGUIController(GUIControllerUpdateable guiController) {
        guiControllers.add(guiController);
    }
    @Override
    public void newMessage(Data data) {
        handleNewMessage(data);
    }

    private void handleNewMessage(Data data) {
        System.out.println("Som v handle Message guiupdatera");
        for (GUIControllerUpdateable guiController : guiControllers) {
            System.out.println("1--");
            guiController.updateGUI(data);
            System.out.println("2--");
        }
    }
}
