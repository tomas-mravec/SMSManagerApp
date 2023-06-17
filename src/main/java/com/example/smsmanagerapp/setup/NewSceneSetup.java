package com.example.smsmanagerapp.setup;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.gui.GUINotifier;
import com.example.smsmanagerapp.gui.controller.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NewSceneSetup {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String resource;
    private List<MessageManager> messageManagers;
    public NewSceneSetup(Stage stage, String resource, List<MessageManager> messageManagers) {
        this.stage = stage;
        this.resource = resource;
        this.messageManagers = messageManagers;
    }

    public void switchToNewScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        root = fxmlLoader.load();
        GUIController guiController = fxmlLoader.getController();
        addMessageManagersToController(guiController);
        System.out.println("Stlacil som button, nizsie bude container controllera: ");
        guiController.testReturn();
        scene = new Scene(root);
        GUINotifier notifier = GUINotifier.getInstance();
        notifier.setCurrentScene(scene);
        notifier.setGuiController(guiController);
        //stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        guiController.loadMessages();
        stage.show();
    }

    private void addMessageManagersToController(GUIController guiController) {
        System.out.println("Dam vobec nejaky kontainer do controllera?: " + "Dlzka menu: " + this.toString() + " array kontainerov je " + messageManagers.size());
        for (MessageManager messageManager : messageManagers) {
            guiController.addMessageManager(messageManager);
            System.out.println("YEP");
        }
    }
}
