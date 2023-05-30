package com.example.smsmanagerapp.gui;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.gui.controller.GUIController;

import java.util.ArrayList;
import java.util.List;

public class GUIUpdaterImpl implements GUIUpdater{

    private List<GUIController> guiControllers;

    public GUIUpdaterImpl() {
        this.guiControllers = new ArrayList<>();
    }

    public void addGUIController(GUIController guiController) {
        guiControllers.add(guiController);
    }
    @Override
    public void newMessage(Data data) {
        handleNewMessage(data);
    }

    private void handleNewMessage(Data data) {
        for (GUIController guiController : guiControllers) {
            guiController.updateGUI(data);
        }
    }
}
