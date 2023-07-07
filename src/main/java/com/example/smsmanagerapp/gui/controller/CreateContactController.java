package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.gui.controller.interfaces.GUIController;
import com.example.smsmanagerapp.table.manager.contact.ContactManager;
import com.example.smsmanagerapp.data.contact.Contact;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateContactController implements GUIController {

    @FXML
    private Button createContactButton;

    @FXML
    private Label conditionNotMetLabel;

    @FXML
    private TextField contactField;

    @FXML
    private TextField numberField;
    private Node menu;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox contactBox;
    private ContactManager contactManager;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //menu = MenuManager.getMenuInstance();
       // rootPane.getChildren().add(0, menu);
        conditionNotMetLabel.setVisible(false);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-color: white;");
        contactBox.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-background-color: white;");
    }

    public void createContact(ActionEvent event) {
        String number = numberField.getText();
        String contactName = contactField.getText();
        int affectedRows = contactManager.updateContactName(number, contactName);
        if(contactName == null || contactName.isEmpty()) {
            conditionNotMetLabel.setText("Meno nemôže byť prázdne");
            conditionNotMetLabel.setVisible(true);
        }
       else if (affectedRows == 1) {
            numberField.setText(null);
            contactField.setText(null);
            conditionNotMetLabel.setVisible(false);
           loadContactsWithName();
       } else if (affectedRows == 0){
            conditionNotMetLabel.setText("Zadané telefónne číslo nebolo nájdené");
            conditionNotMetLabel.setVisible(true);
       }
    }

    private void loadContactsWithName() {
      contactBox.getChildren().clear();
      for (Contact contact : contactManager.getAllContactsWithName()) {
          showContact(contact);
      }
    }

    private void showContact(Contact contact) {
        Platform.runLater(() -> {
            HBox contactData = new HBox();
            contactData.setSpacing(50);
            Label numberLabel = new Label();
            Label nameLabel = new Label();
            nameLabel.setText(contact.getName());
            nameLabel.setStyle("-fx-font-size: 19px;");
            numberLabel.setText(contact.getNumber());
            numberLabel.setStyle("-fx-font-size: 19px;");
            contactData.getChildren().addAll(numberLabel, nameLabel);
            contactBox.getChildren().add(contactData);
        });
    }

    public void setContactManager(ContactManager contactManager) {
        this.contactManager = contactManager;
        loadContactsWithName();
    }

    public void setMenu() {
        rootPane.getChildren().add(0,MenuManager.getMenuInstance());
    }

    public Parent getRoot() {
        return rootPane;
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {

    }

    @Override
    public void loadMessages() {

    }

    @Override
    public void testReturn() {

    }

    @Override
    public MessageRecencyType getRecencyType() {
        return null;
    }
}
