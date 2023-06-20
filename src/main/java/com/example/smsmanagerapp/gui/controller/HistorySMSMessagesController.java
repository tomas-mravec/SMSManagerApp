package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.message.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Contact;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistorySMSMessagesController implements Initializable, GUIController {

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

    }

    @Override
    public void updateGUI(Data data) {
        if (data != null) {
            Platform.runLater(() -> {
                SMSMessage smsMessage = (SMSMessage) data;
                System.out.println("Som v update gui gui controllera");
                System.out.println(((SMSMessage) data).getSender() + " Sprava: " + ((SMSMessage) data).getContent());

                VBox messageContainer = new VBox();
                Label label1 = new Label();
                Contact contact = smsMessage.getContact();
                if (contact.getName() != null && !contact.getName().isEmpty()) {
                    label1.setText("Odosielateľ: " + contact.getName() + "  Číslo: " + smsMessage.getSender());
                } else {
                    label1.setText("Odosielateľ: " + smsMessage.getSender());
                }
                Label label2 = new Label();
                label2.setText("Čas: " + smsMessage.getRecvTime().format(formatter));
                Label label3 = new Label();
                label3.setText("Správa: " + smsMessage.getContent());
//                Label label4 = new Label();
//                label4.setText("Id správy: " + smsMessage.getId());

                label1.setWrapText(true); // Enable text wrapping
                label1.setMaxWidth(Double.MAX_VALUE);
                label2.setWrapText(true); // Enable text wrapping
                label2.setMaxWidth(Double.MAX_VALUE);
                label3.setWrapText(true); // Enable text wrapping
                label3.setMaxWidth(Double.MAX_VALUE);
//                label4.setWrapText(true); // Enable text wrapping
//                label4.setMaxWidth(Double.MAX_VALUE);



                Separator separator = new Separator();
                separator.getStyleClass().add("\\css\\message-separator");

                messageContainer.getChildren().addAll(label1, label3, label2, separator);

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
