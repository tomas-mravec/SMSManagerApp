package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.data.contact.Contact;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistorySMSMessagesController implements Initializable, GUIControllerUpdateable {

    private VBox menu;

    @FXML
    private VBox messageBox;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchField;
    private List<MessageManager> messageManagers;

    @FXML
    private Button resetDateButton;

    private MenuControllerImpl menuController;
    private MessageRecencyType recencyType;
    private String senderFilter;
    @FXML
    private DatePicker datePicker;

    @FXML
    private ScrollPane scrollPane;

    private LocalDate dateFilter;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public HistorySMSMessagesController() {
        messageManagers = new ArrayList<>();
        recencyType = MessageRecencyType.HISTORY_MESSAGE;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu = MenuManager.getMenuInstance();
        menuController = MenuManager.getMenuController();
        rootPane.getChildren().add(0, menu); // Add menuBox as the first child
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("In button listener new search word is " + newValue);
            searchHistoryBySender(newValue);
        });
//        scrollPane.setStyle("-fx-background-color: white;");
//        messageBox.setStyle("-fx-background-color: white;");
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-color: white;");
        messageBox.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-background-color: white;");
        messageBox.getStylesheets().add(getClass().getResource(ResourceHelper.getMessageLabelsStyle()).toExternalForm());
    }

    @Override
    public void updateGUI(Data data) {
        if (data != null) {
            Platform.runLater(() -> {
                SMSMessage smsMessage = (SMSMessage) data;
                System.out.println("Som v update gui gui controllera");
                System.out.println(((SMSMessage) data).getSender() + " Sprava: " + ((SMSMessage) data).getContent());

                boolean contactnameExists = false;
                VBox messageContainer = new VBox();
                VBox downContainer = new VBox();
                HBox messageDataContainer = new HBox();
                Label senderLabel = new Label();
                Label senderNumberLabel = new Label();
                Label contactNameLabel = new Label();
                Label contactLabel = new Label();
                Contact contact = smsMessage.getContact();
                senderLabel.setText("Číslo: ");
                senderNumberLabel.setText(smsMessage.getSender());
                if (contact.getName() != null && !contact.getName().isEmpty()) {
                    contactLabel.setText("Kontakt: ");
                    contactNameLabel.setText(contact.getName());
                    contactnameExists = true;
                }

                Label timeLabel = new Label();
                timeLabel.setText("Čas: ");
                Label timeValueLabel = new Label();
                timeValueLabel.setText(smsMessage.getRecvTime().format(formatter));
                Label messageLabel = new Label();
                messageLabel.setText(smsMessage.getContent());

                senderLabel.getStyleClass().add("label-normal");
                contactLabel.getStyleClass().add("label-normal");
                senderNumberLabel.getStyleClass().add("label-normal");
                contactNameLabel.getStyleClass().add("label-normal");
                messageLabel.getStyleClass().add("label-normal");
                timeLabel.getStyleClass().add("label-normal");
                timeValueLabel.getStyleClass().add("label-normal");

                senderLabel.getStyleClass().add("label-orange");
                contactLabel.getStyleClass().add("label-orange");
                timeLabel.getStyleClass().add("label-orange");

                senderNumberLabel.getStyleClass().add("label-right-padding");
                contactNameLabel.getStyleClass().add("label-right-padding");
                timeValueLabel.getStyleClass().add("label-right-padding");

                // messageContainer.getStyleClass().add("vbox-spacing");
                // messageDataContainer.getStyleClass().add("hbox-bottom-margin");

                //downContainer.getStyleClass().add("padding-top");
                //messageDataContainer.setSpacing(50);
                messageDataContainer.setPadding(new Insets(20,0,0,0));
                downContainer.setMargin(downContainer, new Insets(20,0,0,0));
                downContainer.setSpacing(20);
//                senderLabel.setWrapText(true); // Enable text wrapping
//                senderLabel.setMaxWidth(Double.MAX_VALUE);
//                timeLabel.setWrapText(true); // Enable text wrapping
//                timeLabel.setMaxWidth(Double.MAX_VALUE);
//                messageLabel.setWrapText(true); // Enable text wrapping
//                messageLabel.setMaxWidth(Double.MAX_VALUE);


                Separator separator = new Separator();
                separator.getStyleClass().add("\\css\\message-separator");

                if (contactnameExists) {
                    messageDataContainer.getChildren().addAll(senderLabel, senderNumberLabel, contactLabel, contactNameLabel, timeLabel, timeValueLabel);
                } else {
                    messageDataContainer.getChildren().addAll(senderLabel, senderNumberLabel, timeLabel, timeValueLabel);
                }


                downContainer.getChildren().addAll(messageLabel,separator);
                messageContainer.getChildren().addAll(messageDataContainer, downContainer);
//        messageContainer.setMaxWidth(Double.MAX_VALUE);
//        messageBox.setMaxWidth(Double.MAX_VALUE);
//        VBox.setVgrow(messageBox, Priority.ALWAYS);
//        VBox.setVgrow(messageContainer, Priority.ALWAYS);

                messageBox.getChildren().add(messageContainer);

            });
        }
    }

    public void dateSelected(ActionEvent event) {
        dateFilter = datePicker.getValue();
        eraseMessagesOnGUI();
//        for (MessageManager messageManager : messageManagers) {
//            for (Data data : messageManager.getAllSeenByDate(dateFilter)) {
//                updateGUI(data);
//            }
//        }
        filterMesssages();
    }

    private void filterMesssages() {
        for (MessageManager messageManager : messageManagers) {
            for (Data data : messageManager.getFilteredMessages(senderFilter, dateFilter)) {
                updateGUI(data);
            }
        }
    }

    private void searchHistoryBySender(String newValue) {
        System.out.println("in search history by sender New value is: " + newValue);
        senderFilter = newValue;
        eraseMessagesOnGUI();
        System.out.println("I erased messages");
        filterMesssages();
//        for (MessageManager messageManager : messageManagers) {
//            for (Data data : messageManager.getFilteredMessages(newValue)) {
//                updateGUI(data);
//            }
//        }
    }

    private void eraseMessagesOnGUI() {
        messageBox.getChildren().clear();
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

    public void resetDatePicker(ActionEvent event) {
        datePicker.setValue(null);
    }

    @Override
    public void testReturn() {

    }

    public MessageRecencyType getRecencyType() {
        return recencyType;
    }
}
