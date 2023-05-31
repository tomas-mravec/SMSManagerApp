package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NewSMSMessageController implements GUIController {

    @FXML
    private VBox messageBox;

    @Override
    public void updateGUI(Data data) {
        SMSMessage smsMessage = (SMSMessage) data;
        System.out.println("Som v update gui gui controllera");
        System.out.println(((SMSMessage)data).getSender() + " Sprava: " + ((SMSMessage)data).getContent());

        VBox messageContainer = new VBox();
        Label label1 = new Label();
        label1.setText("Odosielateľ: " + smsMessage.getSender());
        Label label2 = new Label();
        label2.setText("Čas: " + smsMessage.getRecvTime());
        Label label3 = new Label();
        label3.setText("Správa: " + smsMessage.getContent());


        label1.setWrapText(true); // Enable text wrapping
        label1.setMaxWidth(Double.MAX_VALUE);
        label2.setWrapText(true); // Enable text wrapping
        label2.setMaxWidth(Double.MAX_VALUE);
        label3.setWrapText(true); // Enable text wrapping
        label3.setMaxWidth(Double.MAX_VALUE);

        Separator separator = new Separator();
        separator.getStyleClass().add("message-separator");

        messageContainer.getChildren().addAll(label1, label2, label3, separator);

        messageContainer.setMaxWidth(Double.MAX_VALUE);
        messageBox.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(messageBox, Priority.ALWAYS);
        VBox.setVgrow(messageContainer, Priority.ALWAYS);

        messageBox.getChildren().add(messageContainer);

    }


}