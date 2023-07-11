package com.example.smsmanagerapp.gui.controller.send;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;

public class MessageOutBlockController {

    @FXML
    private Label receiverLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private AnchorPane messageOutPane;

    @FXML
    private Button deleteMessageOutButton;

    public Button getDeleteMessageOutButton() {
        return deleteMessageOutButton;
    }

    public Parent getRoot() {
        return messageOutPane;
    }

    public void setReceiverLabelText(String text) {
        receiverLabel.setText(text);
    }

    public void setTimeLabelText(String text) {
        timeLabel.setText(text);
    }

    public void setMessageLabelText(String text) {
        this.messageLabel.setText(text);
    }
}
