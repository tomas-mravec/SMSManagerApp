package com.example.smsmanagerapp.gui.notifier;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import javafx.scene.Scene;

public class GUINotifier {

    private static volatile GUINotifier notifier;
    private Scene currentScene;
    private GUIControllerUpdateable guiController;
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
    public void setGuiController(GUIControllerUpdateable guiController) {
        this.guiController = guiController;
    }

    public void newMessage(Data data) {
      //  if (guiController != null && guiController.getRecencyType() == MessageRecencyType.NEW_MESSAGE) {
        if (guiController != null)
            guiController.updateGUI(data);
        //}
    }
}
