package com.example.smsmanagerapp.gui;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.gui.controller.GUIController;
import javafx.scene.Scene;

public class GUINotifier {

    private static volatile GUINotifier notifier;
    private Scene currentScene;
    private GUIController guiController;
    private GUINotifier() {
        // Private constructor to prevent instantiation
    }

    public static GUINotifier getInstance() {
        GUINotifier local = notifier;
        if (local == null) {
            synchronized (GUINotifier.class) {
                local = notifier;
                if (local == null) {
                    notifier = local = new GUINotifier();
                }
            }
        }
        return local;
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }
    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }

    public void newMessage(Data data) {
        guiController.updateGUI(data);
    }
}
