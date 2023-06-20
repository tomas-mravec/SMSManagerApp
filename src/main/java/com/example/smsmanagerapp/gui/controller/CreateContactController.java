package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.contact.ContactManager;
import com.example.smsmanagerapp.data.Contact;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateContactController implements Initializable {

    @FXML
    private Button createContactButton;

    @FXML
    private TextField contactField;

    @FXML
    private TextField numberField;
    private VBox menu;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox contactBox;
    private ContactManager contactManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu = MenuManager.getMenuInstance();
        rootPane.getChildren().add(0, menu);
    }

    public void createContact(ActionEvent event) {
        String number = numberField.getText();
        String contactName = contactField.getText();
        numberField.setText(null);
        contactField.setText(null);
        contactManager.updateContactName(number, contactName);
        loadContactsWithName();
    }

    private void loadContactsWithName() {
      contactBox.getChildren().clear();
      for (Contact contact : contactManager.getAllContactsWithName()) {
          showContact(contact);
      }
    }

    private void showContact(Contact contact) {
        Platform.runLater(() -> {
            Label label1 = new Label();
            label1.setText(contact.getNumber() + "           " + contact.getName());
            contactBox.getChildren().add(label1);
        });
    }

    public void setContactManager(ContactManager contactManager) {
        this.contactManager = contactManager;
        loadContactsWithName();
    }
}
