package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.data.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NewSMSMessageController implements GUIController{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void updateGUI(Data data) {

    }
}