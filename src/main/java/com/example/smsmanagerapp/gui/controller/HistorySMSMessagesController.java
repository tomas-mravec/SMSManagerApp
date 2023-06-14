package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistorySMSMessagesController implements Initializable, GUIController {

    private VBox menu;

    @FXML
    private VBox messageBox;

    @FXML
    private AnchorPane rootPane;
    private List<MessageManager> messageManagers;

    private MenuControllerImpl menuController;
    private MessageRecencyType recencyType;


    public HistorySMSMessagesController() {
        messageManagers = new ArrayList<>();
        recencyType = MessageRecencyType.HISTORY_MESSAGE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu = MenuManager.getMenuInstance();
        menuController = MenuManager.getMenuController();
        rootPane.getChildren().add(0, menu); // Add menuBox as the first child
    }

    @Override
    public void updateGUI(Data data) {
        Platform.runLater(() -> {
            SMSMessage smsMessage = (SMSMessage) data;
            System.out.println("Som v update gui gui history controllera");
            System.out.println(((SMSMessage) data).getSender() + " Sprava: " + ((SMSMessage) data).getContent());

            VBox messageContainer = new VBox();
            Label label1 = new Label();
            label1.setText("Odosielateľ: " + smsMessage.getSender());
//        Label label2 = new Label();
//        label2.setText("Čas: " + smsMessage.getRecvTime());
            Label label3 = new Label();
            label3.setText("Správa: " + smsMessage.getContent());


            label1.setWrapText(true); // Enable text wrapping
            label1.setMaxWidth(Double.MAX_VALUE);
//        label2.setWrapText(true); // Enable text wrapping
//        label2.setMaxWidth(Double.MAX_VALUE);
            label3.setWrapText(true); // Enable text wrapping
            label3.setMaxWidth(Double.MAX_VALUE);


//            Button sendToHistory = new Button();
//            sendToHistory.setText("Videné");
//            sendToHistory.setOnAction(event -> {
//                sendMessageToHistory(smsMessage);
//                messageBox.getChildren().remove(messageContainer);
//            });

            Separator separator = new Separator();
            separator.getStyleClass().add("\\css\\message-separator");

            messageContainer.getChildren().addAll(label1, label3,separator);

//        messageContainer.setMaxWidth(Double.MAX_VALUE);
//        messageBox.setMaxWidth(Double.MAX_VALUE);
//        VBox.setVgrow(messageBox, Priority.ALWAYS);
//        VBox.setVgrow(messageContainer, Priority.ALWAYS);

            messageBox.getChildren().add(messageContainer);

        });
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {
        this.messageManagers.add(messageManager);
    }

    @Override
    public void loadMessages() {
        System.out.println("Loadujem data z history controllera budem vnutry?");
        for (MessageManager messageManager : messageManagers) {
            for (Data data : messageManager.getAllSeenMessages()) {
                System.out.println("YEP");
                updateGUI(data);
            }
        }
    }

    @Override
    public void testReturn() {

    }

    public MessageRecencyType getRecencyType() {
        return recencyType;
    }
}
