package com.example.smsmanagerapp;

import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.setup.ApplicationSetup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new-sms-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SMSManager");
        stage.setScene(scene);
        stage.show();
        MenuManager.getMenuController().setStage(stage);
        GUIControllerUpdateable guiController = fxmlLoader.getController();
        Platform.runLater(() -> {
            ApplicationSetup setup = new ApplicationSetup(scene, guiController);
            setup.setUp();
        });
    }


    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}