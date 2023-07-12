package com.example.smsmanagerapp.gui.controller.message;

import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.DeletableMessagesController;
import com.example.smsmanagerapp.gui.controller.menu.MenuControllerImpl;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewSMSMessagesController implements GUIControllerUpdateable, DeletableMessagesController {

    @FXML
    private VBox messageBox;

    private Node menu;
    private MenuControllerImpl menuController;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ScrollPane scrollPane;
    private List<MessageManager> messageManagers;
    private MessageRecencyType recencyType;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public NewSMSMessagesController() {
        messageManagers = new ArrayList<>();
        recencyType = MessageRecencyType.NEW_MESSAGE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //menu = MenuManager.getMenuInstance();
        //menuController = MenuManager.getMenuController();
        //rootPane.getChildren().add(0, menu);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-color: white;");
        messageBox.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-background-color: white;");
        messageBox.getStylesheets().add(getClass().getResource(ResourceHelper.getMessageLabelsStyle()).toExternalForm());
    }

    public void setMenu() {
        rootPane.getChildren().add(0, MenuManager.getMenuInstance());
    }

    public void loadMessages() {
        System.out.println("Loadujem data z gui controllera");
        messageBox.getChildren().clear();
        for (MessageManager messageManager : messageManagers) {
           for (Data data : messageManager.getAllNewMessages()) {
                   updateGUI(data);
           }
        }
    }
    public void resetDatePicker(ActionEvent event) {
        //datePicker.setValue(null);
    }
    @Override
    public void updateGUI(Data data) {
        if (data != null) {
            Platform.runLater(() -> {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageBlockResource()));
                MessageBlockController messageBlockController;
                try {
                    fxmlLoader.load();
                    messageBlockController = fxmlLoader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                SMSMessage smsMessage = (SMSMessage) data;

                if (smsMessage.getContact().getName() != null && !smsMessage.getContact().getName().isEmpty()) {
                    messageBlockController.setContactLabelText(smsMessage.getContact().getName());
                } else {
                    messageBlockController.setContactLabelText(smsMessage.getContact().getNumber());
                }
                messageBlockController.setTimeLabelText(smsMessage.getRecvTime().format(formatter));
                messageBlockController.setMessageLabelText(smsMessage.getContent());
                Separator separator = new Separator();
                messageBox.getChildren().addAll(messageBlockController.getRoot(), separator);



//                SMSMessage smsMessage = (SMSMessage) data;
//                System.out.println("Som v update gui gui controllera");
//                System.out.println(((SMSMessage) data).getContact().getNumber() + " Sprava: " + ((SMSMessage) data).getContent());
//
//                boolean contactnameExists = false;
//                VBox messageContainer = new VBox();
//                VBox downContainer = new VBox();
//                HBox messageDataContainer = new HBox();
//                Label senderLabel = new Label();
//                Label senderNumberLabel = new Label();
//                Label contactNameLabel = new Label();
//                Label contactLabel = new Label();
//                Contact contact = smsMessage.getContact();
//                senderLabel.setText("Číslo: ");
//                senderNumberLabel.setText(contact.getNumber());
//                if (contact.getName() != null && !contact.getName().isEmpty()) {
//                    contactLabel.setText("Kontakt: ");
//                    contactNameLabel.setText(contact.getName());
//                    contactnameExists = true;
//                }
//
//                Label timeLabel = new Label();
//                timeLabel.setText("Čas: ");
//                Label timeValueLabel = new Label();
//                timeValueLabel.setText(smsMessage.getRecvTime().format(formatter));
//                Label messageLabel = new Label();
//                messageLabel.setText(smsMessage.getContent());
//
//                senderLabel.getStyleClass().add("label-normal");
//                contactLabel.getStyleClass().add("label-normal");
//                senderNumberLabel.getStyleClass().add("label-normal");
//                contactNameLabel.getStyleClass().add("label-normal");
//                messageLabel.getStyleClass().add("label-normal");
//                timeLabel.getStyleClass().add("label-normal");
//                timeValueLabel.getStyleClass().add("label-normal");
//
//                senderLabel.getStyleClass().add("label-orange");
//                contactLabel.getStyleClass().add("label-orange");
//                timeLabel.getStyleClass().add("label-orange");
//
//                senderNumberLabel.getStyleClass().add("label-right-padding");
//                contactNameLabel.getStyleClass().add("label-right-padding");
//                timeValueLabel.getStyleClass().add("label-right-padding");
//
//               // messageContainer.getStyleClass().add("vbox-spacing");
//               // messageDataContainer.getStyleClass().add("hbox-bottom-margin");
//
//                //downContainer.getStyleClass().add("padding-top");
//                //messageDataContainer.setSpacing(50);
//                messageDataContainer.setPadding(new Insets(20,0,0,0));
//                downContainer.setMargin(downContainer, new Insets(20,0,0,0));
//                downContainer.setSpacing(20);
////                senderLabel.setWrapText(true); // Enable text wrapping
////                senderLabel.setMaxWidth(Double.MAX_VALUE);
////                timeLabel.setWrapText(true); // Enable text wrapping
////                timeLabel.setMaxWidth(Double.MAX_VALUE);
////                messageLabel.setWrapText(true); // Enable text wrapping
////                messageLabel.setMaxWidth(Double.MAX_VALUE);
//
//
//                Button sendToHistory = new Button();
//                sendToHistory.setText("Videné");
//                sendToHistory.setOnAction(event -> {
//                    System.out.println("Bol stlaceny button na odoslanie do historie");
//                    sendMessageToHistory(smsMessage);
//                    messageBox.getChildren().remove(messageContainer);
//                });
//
//                Separator separator = new Separator();
////                separator.getStylesheets().add(getClass().getResource("/com/example/smsmanagerapp/css/message-separator.css").toExternalForm());
////                separator.getStyleClass().add("message-separator");
//                 //separator.setStyle("-fx-border-color:#D2691E;-fx-border-width:5;");
////                separator.setStyle(""
////                        + "-fx-border-width: 3px;"
////                        + "-fx-border-color: black;"
////                        + "-fx-padding: 0px;"
////                        + "");
//
//
//                if (contactnameExists) {
//                    messageDataContainer.getChildren().addAll(senderLabel, senderNumberLabel, contactLabel, contactNameLabel, timeLabel, timeValueLabel);
//                } else {
//                    messageDataContainer.getChildren().addAll(senderLabel, senderNumberLabel, timeLabel, timeValueLabel);
//                }
//
//
//                downContainer.getChildren().addAll(messageLabel, sendToHistory, separator);
//                messageContainer.getChildren().addAll(messageDataContainer, downContainer);
////        messageContainer.setMaxWidth(Double.MAX_VALUE);
////        messageBox.setMaxWidth(Double.MAX_VALUE);
////        VBox.setVgrow(messageBox, Priority.ALWAYS);
////        VBox.setVgrow(messageContainer, Priority.ALWAYS);
//
//                messageBox.getChildren().add(messageContainer);

            });
        }
    }

    private void sendMessageToHistory(SMSMessage smsMessage) {
        for (MessageManager messageManager : messageManagers) {
           messageManager.setMessageAsSeen(smsMessage);
        }
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {
        if (!messageManagers.contains(messageManager)) {
            messageManagers.add(messageManager);
        }
    }

    public Parent getRoot() {
        return rootPane;
    }

    public void testReturn() {
        //System.out.println("GUIController sa ozyva " + messageManagers.get(0).toString());
    }

    public MessageRecencyType getRecencyType() {
        return recencyType;
    }

    @Override
    public void markToDelete(BlockController blockController, boolean checked) {

    }

    @Override
    public void unMarkToDelete(BlockController blockController) {

    }
}