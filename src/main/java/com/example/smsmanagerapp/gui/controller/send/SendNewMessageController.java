package com.example.smsmanagerapp.gui.controller.send;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.data.contact.SMSMessageOut;
import com.example.smsmanagerapp.gui.controller.contact.ContactBlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIController;
import com.example.smsmanagerapp.gui.controller.message.MessageBlockController;
import com.example.smsmanagerapp.table.manager.contact.ContactManager;
import com.example.smsmanagerapp.data.contact.Contact;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.sender.MessageSender;
import com.example.smsmanagerapp.table.manager.group.contact.GroupContactManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.messageout.MessageOutManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendNewMessageController implements GUIController {

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextArea textArea;

    @FXML
    private AnchorPane rootPane;


    private MessageSender messageSender;

    private Node menu;

    private ContactManager contactManager;
    private GroupContactManager groupContactManager;
    private MessageOutManager messageOutManager;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private Timer timer;
    private LocalDateTime scheduledMessageTime;

    @FXML
    private VBox messageBox;

    private List<String> contacts;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @FXML
    private TextField receiverField;

    @FXML
    private Button addReceiverButton;

    @FXML
    private VBox receiverBox;

    public SendNewMessageController() {
        contacts = new ArrayList<>();
        timer = new Timer();
        //scheduledMessageTime = LocalDateTime.of(2023, 6, 29, 14, 58, 40);;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //menu = MenuManager.getMenuInstance();
        //rootPane.getChildren().add(0, menu);
    }

    public void addReceiver() {
        Platform.runLater(() -> {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getReceiverBlockResource()));
            ReceiverBlockController receiverBlockController;
            try {
                fxmlLoader.load();
                receiverBlockController = fxmlLoader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String receiver = receiverField.getText();
            receiverBlockController.setReceiverLabelText(receiver);
            contacts.add(receiver);
            receiverBox.getChildren().add(receiverBlockController.getRootPane());
            receiverField.clear();

            Button removeButton = receiverBlockController.getRemoveButton();
            removeButton.setOnAction(event -> {
                receiverBox.getChildren().remove(receiverBlockController.getRootPane());
                contacts.remove(receiver);
            });

        });
    }

    public void removeAllReceivers() {
        contacts.clear();
        receiverBox.getChildren().clear();
    }

    public void setMenu() {
        rootPane.getChildren().add(0, MenuManager.getMenuInstance());
    }
    public void sendMessage() {

        String messageContent = textArea.getText();

        if (scheduledMessageTime == null) {
            send(new ArrayList<>(contacts), messageContent);
        } else {
            List<String> addedContacts = new ArrayList<>(contacts);
            Runnable task = () -> {
                Platform.runLater(() -> {
                    send(new ArrayList<>(addedContacts), messageContent);
                    System.out.println("Scheduled task executed");
                    for (String contact : contacts) {
                        System.out.println(contact);
                    }
                });
            };

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime executionDateTime = scheduledMessageTime; // Replace with your desired execution LocalDateTime

            long initialDelay = now.until(executionDateTime, ChronoUnit.SECONDS);
            scheduler.schedule(task, initialDelay, TimeUnit.SECONDS);
        }

        contacts.clear();
    }

    public synchronized void send(List<String> contacts, String message) {
        System.out.println("Text je: " + message);
        boolean messageSent = false;
        boolean groupContactCreated = false;

        for (String receiver : contacts) {
            Optional<Contact> contactOptional = contactManager.findContact(receiver);
            //ak kontakt existuje v databaze tak ho nemusime vytvarat
            if (contactOptional.isPresent()) {
                System.out.println("Kontakt pri odosielani existuje");
                messageSender.sendMessage(message, contactOptional.get().getNumber());
                groupContactCreated = groupContactManager.addGroupContact(contactOptional.get(), groupContactManager.getId());
//               if (groupContractCreated) {
//                   System.out.println("Uloziavam out message do databazy");
//                   messageOutManager.createNewOutMessage(message, groupContactManager.getId());
//               }
            } else {
                //ak neni present, potrebujeme zistit ci ta sprava bola spravne odoslana lebo nechceme ulozit garbage spravy ktore neboli prijmute a odoslane
                messageSender.sendMessage(message, receiver);
                //skontrolujeme ci gsm uspesne poslala spravu, ak hej tak cislo asi existuje a mozeme ho ulozit do kontatov

                messageSent = true;
                if (messageSent) {
                    Contact newContact = new Contact(receiver);
                    boolean contactCreated = contactManager.createNewContact(newContact);
                    if (contactCreated) {
                        boolean groupContractCreated = groupContactManager.addGroupContact(newContact, groupContactManager.getId());
//                       if (groupContractCreated) {
//                           messageOutManager.createNewOutMessage(message, groupContactManager.getId());
//                       }
                    }
                }
            }
        }
        messageOutManager.createNewOutMessage(message, groupContactManager.getId());
        groupContactManager.setId(groupContactManager.getId() + 1);
        clearMessages();
        loadMessages();
        // contactManager.createNewContact(new Contact(receiver));
        // messageSender.sendMessage(message, receiver);

    }

    public void clearMessages() {
        messageBox.getChildren().clear();
    }

    @Override
    public void addMessageManager(MessageManager messageManager) {

    }

    public void loadMessages() {
        for (Data data : messageOutManager.getAllMessages()) {
            updateGUI(data);
        }
    }

    @Override
    public void testReturn() {

    }

    @Override
    public MessageRecencyType getRecencyType() {
        return null;
    }

    private void updateGUI(Data data) {
        if (data != null) {
            Platform.runLater(() -> {
                SMSMessageOut messageOut = (SMSMessageOut) data;

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageOutBlock()));
                MessageOutBlockController messageOutBlockController;
                try {
                    fxmlLoader.load();
                    messageOutBlockController = fxmlLoader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String receivers = "";
                for (Contact contact :messageOut.getGroupContact().getContacts()) {
                    if (contact.getName() != null && !contact.getName().isEmpty()) {
                        receivers += contact.getName() + " ";
                    }
                    else {
                        receivers += contact.getNumber() + " ";
                    }
                }
                messageOutBlockController.setReceiverLabelText(receivers);
                messageOutBlockController.setTimeLabelText(messageOut.getTime().format(formatter));
                messageOutBlockController.setMessageLabelText(messageOut.getContent());

                messageOutBlockController.getDeleteMessageOutButton().setOnAction(event -> {
                    messageBox.getChildren().remove(messageOutBlockController.getRoot());
                    messageOutManager.deleteMessage(messageOut.getId());
                });

                messageBox.getChildren().add(messageOutBlockController.getRoot());
               // HBox messageDataContainer = new HBox();
                //messageDataContainer.setSpacing(20);

//                for (Contact contact:messageOut.getGroupContact().getContacts()) {
//                    //Label contactNumber = new Label(contact.getNumber());
//                    // messageDataContainer.getChildren().add(contactNumber);
//                    if (contact.getName() != null && !contact.getName().isEmpty()) {
//                        Label contactName = new Label("Komu: " + contact.getName());
//                        messageBox.getChildren().addAll(contactName);
//                        //messageDataContainer.getChildren().add(contactName);
//                    } else {
//                        Label contactNumber = new Label("Komu: " + contact.getNumber());
//                        messageBox.getChildren().addAll(contactNumber);
//                    }
//
//                }
//                    Label time = new Label("Čas: " + messageOut.getTime().format(formatter));
//                   // messageDataContainer.getChildren().add(time);
//                    Label content = new Label(messageOut.getContent());
//                    Separator separator = new Separator();
//                    messageBox.getChildren().addAll(time, content, separator);
            });
        }
    }

    public Parent getRoot() {
        return rootPane;
    }


    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    public void setContactManager(ContactManager contactManager) {
        this.contactManager = contactManager;
    }
    public void setGroupContactManager(GroupContactManager groupContactManager){this.groupContactManager = groupContactManager;}
    public void setMessageOutManager(MessageOutManager messageOutManager){this.messageOutManager = messageOutManager;}


}
