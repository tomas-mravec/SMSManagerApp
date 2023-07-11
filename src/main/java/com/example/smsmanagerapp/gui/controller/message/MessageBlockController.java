package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageBlockController implements Initializable {

    @FXML
    private Button seenButton;

    @FXML
    private CheckBox seenCheckBox;

    @FXML
    private Label contactLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private CheckBox deleteCheckBox;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageBlockResource()));
//        try {
//            fxmlLoader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Parent getRoot() {
        return rootPane;
    }

    public void setContactLabelText(String text) {
        contactLabel.setText(text);
    }

    public void setTimeLabelText(String text) {
        timeLabel.setText(text);
    }

    public void setMessageLabelText(String text) {
        this.messageLabel.setText(text);
    }


}
