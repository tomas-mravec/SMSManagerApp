package com.example.smsmanagerapp.gui.controller.contact;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ContactBlockController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberLabel;

    public void setNumberLabelText(String text) {
        this.numberLabel.setText(text);
    }

    public void setNameLabelText(String text) {
        this.nameLabel.setText(text);
    }

    public Parent getRoot() {
        return rootPane;
    }
}
