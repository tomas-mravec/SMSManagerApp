package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.gui.controller.container.SMSSceneControllerContainer;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.table.manager.contact.ContactManager;
import com.example.smsmanagerapp.table.manager.contact.ContactManagerImpl;
import com.example.smsmanagerapp.table.manager.group.contact.GroupContactManager;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.gui.notifier.GUINotifier;
import com.example.smsmanagerapp.manager.ButtonStyleManager;
import com.example.smsmanagerapp.sender.MessageSender;
import com.example.smsmanagerapp.setup.NewSceneSetup;
import com.example.smsmanagerapp.table.manager.messageout.MessageOutManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MenuControllerImpl {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private List<MessageManager> messageManagers;

    @FXML
    private Button switchToNewMessagesButton;
    @FXML
    private Button switchToHistoryButton;
    @FXML
    private Button switchToContactsButton;

    private ButtonStyleManager buttonStyleManager;
    private List<Integer> intList;
    private ContactManager contactManager;
    private String highlightedButton;
    private MessageSender messageSender;
    private GroupContactManager groupContactManager;
    private MessageOutManager messageOutManager;

    private Connection connection;
    private SMSSceneControllerContainer sceneControllerContainer;

    public MenuControllerImpl() {
        messageManagers = new ArrayList<>();
        intList = new ArrayList<>();

    }


    @FXML
    private void initialize() {
        buttonStyleManager = new ButtonStyleManager();
        buttonStyleManager.addButton(switchToNewMessagesButton);
        buttonStyleManager.addButton(switchToHistoryButton);
        buttonStyleManager.addButton(switchToContactsButton);
        this.highlightedButton = "NewSMS";
       // switchToNewMessagesButton.setStyle(buttonStyleManager.getOriginalStyle(switchToNewMessagesButton) + "-fx-background-color: #81A5B0;");
    }

    /// skus dat random generator a nech menubox vypise to random cislo ci to je stale ta ista instancia a nie ina
    // je to confirmed je to nova instancia, treba zistit preco a da sa to vyriesit implementaciou singletona
    public void switchToNewSMSScene(ActionEvent event) throws IOException {
        System.out.println("Stlacil som tlacidlo, length array je: " + this.messageManagers.size());
        System.out.println("Stlacil som tlacidlo, int list ma length: " + intList.size());

        Scene scene = sceneControllerContainer.getNewSMSMessageScene();
        if (stage.getScene() != scene) {
            GUINotifier guiNotifier = GUINotifier.getInstance();
            guiNotifier.setCurrentScene(scene);
            NewSMSMessagesController newSMSMessagesController = sceneControllerContainer.getNewSMSMessagesController();
            guiNotifier.setGuiController(newSMSMessagesController);
            newSMSMessagesController.setMenu();
            stage.setScene(scene);

            highlightedButton = "NewSMS";
            changeButtonsColor(highlightedButton);
        }
    }

    private void changeButtonsColor(String button) {
//        switchToHistoryButton.setStyle(buttonStyleManager.getOriginalStyle(switchToHistoryButton) + "-fx-background-color: #ADD8E6;");
//        switchToNewMessagesButton.setStyle(buttonStyleManager.getOriginalStyle(switchToNewMessagesButton) + "-fx-background-color: #ADD8E6;");
//        switchToContactsButton.setStyle(buttonStyleManager.getOriginalStyle(switchToContactsButton) + "-fx-background-color: #ADD8E6;");
//        System.out.println("Length switchToContactsButton stylu je: " + switchToNewMessagesButton.getStyle().length());
//        if (button.equals("NewSMS")) {
//            switchToNewMessagesButton.setStyle(buttonStyleManager.getOriginalStyle(switchToNewMessagesButton) + "-fx-background-color: #81A5B0;");
//        }
//        if (button.equals("History")) {
//            switchToHistoryButton.setStyle(buttonStyleManager.getOriginalStyle(switchToHistoryButton) + "-fx-background-color: #81A5B0;");
//        }
//        if (button.equals("Contacts")) {
//            switchToContactsButton.setStyle(buttonStyleManager.getOriginalStyle(switchToContactsButton) + "-fx-background-color: #81A5B0;");
//        }
    }

    //vytvor kontainery pre messages kde sa budu uloziavat messages, tieto kontajneri budu observers listenera a nie gui updater, ked pride nova sprava, staci aby bol GUI updater len jeden
    // lebo aktualna scena je len jedna a mozes sa spytat ze ci je instance of napr UpdateableSMSMessage a ak hej tak mu data posli ak nie tak mu neopsielaj


    public void switchToHistoryScene(ActionEvent event) throws IOException {
        System.out.println("Stlacil som tlacidlo, length array je: " + this.messageManagers.size());
        System.out.println("Stlacil som tlacidlo, int list ma length: " + intList.size());

        Scene scene = sceneControllerContainer.getHistorySMSMessagesScene();
        if (stage.getScene() != scene) {
            GUINotifier guiNotifier = GUINotifier.getInstance();
            guiNotifier.setCurrentScene(null);
            sceneControllerContainer.getHistorySMSMessagesController().setMenu();
            stage.setScene(scene);

//        NewSceneSetup newSceneSetup = new NewSceneSetup(stage, ResourceHelper.getHistorySMSSceneResource(), messageManagers);
//        newSceneSetup.switchToNewScene();
            highlightedButton = "History";
            changeButtonsColor(highlightedButton);
        }
    }

    public void switchToCreateNewContact(ActionEvent event) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smsmanagerapp/create-contact-view.fxml"));
//        root = fxmlLoader.load();
//        CreateContactController controller = fxmlLoader.getController();
//        controller.setContactManager(contactManager);
//        scene = new Scene(root);
//        GUINotifier notifier = GUINotifier.getInstance();
//        notifier.setCurrentScene(scene);
//        notifier.setGuiController(null);
//        stage.setScene(scene);
//        stage.show();

        Scene scene = sceneControllerContainer.getCreateContactScene();
        if (stage.getScene() != scene) {
            CreateContactController createContactController = sceneControllerContainer.getCreateContactController();
            createContactController.setMenu();
            GUINotifier guiNotifier = GUINotifier.getInstance();
            guiNotifier.setCurrentScene(null);
            stage.setScene(scene);

            highlightedButton = "Contacts";
            changeButtonsColor(highlightedButton);
        }
    }

    public void switchToSendMessageScene() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smsmanagerapp/send-message-view.fxml"));
//        root = fxmlLoader.load();
//        SendNewMessageController newMessageController = fxmlLoader.getController();
//        newMessageController.setMessageSender(messageSender);
//        newMessageController.setContactManager(new ContactManagerImpl(connection));
//        newMessageController.setGroupContactManager(groupContactManager);
//        newMessageController.setMessageOutManager(messageOutManager);
//        newMessageController.loadMessages();
//
//        scene = new Scene(root);
//        GUINotifier notifier = GUINotifier.getInstance();
//        notifier.setCurrentScene(scene);
//        notifier.setGuiController(null);
//        stage.setScene(scene);
//        stage.show();


        Scene scene = sceneControllerContainer.getSendNewMessageScene();
        if (stage.getScene() != scene) {
            sceneControllerContainer.getSendNewMessageController().setMenu();
            GUINotifier guiNotifier = GUINotifier.getInstance();
            guiNotifier.setCurrentScene(null);
            stage.setScene(scene);

//        highlightedButton = "Contacts";
//        changeButtonsColor(highlightedButton);
        }
    }

    public void newMessagesButtonMouseEntered() {
        //switchToNewMessagesButton.setStyle(buttonStyleManager.getOriginalStyle(switchToContactsButton) + "-fx-background-color: #81A5B0;");
        //switchToNewMessagesButton.setText("KWAB");
    }

    public void newMessagesButtonMouseExited() {
       // if (!highlightedButton.equals("NewSMS"))
        //switchToNewMessagesButton.setStyle(buttonStyleManager.getOriginalStyle(switchToNewMessagesButton) + "-fx-background-color: #ADD8E6;");
        //switchToNewMessagesButton.setText("Nové Správy");
    }

    public void historyButtonMouseEntered() {
       // switchToHistoryButton.setStyle(buttonStyleManager.getOriginalStyle(switchToHistoryButton) + "-fx-background-color: #81A5B0;");
    }
    public void historyButtonMouseExited() {
       // if (!highlightedButton.equals("History"))
        //switchToHistoryButton.setStyle(buttonStyleManager.getOriginalStyle(switchToHistoryButton) + "-fx-background-color: #ADD8E6;");
    }

   public void contactsButtonMouseEntered() {
       System.out.println("Kontakty entered");
      // switchToContactsButton.setStyle(buttonStyleManager.getOriginalStyle(switchToContactsButton) + "-fx-background-color: #81A5B0;");
   }
   public void contactsButtonMouseExited() {
        //if (!highlightedButton.equals("Contacts"))
            //switchToContactsButton.setStyle(buttonStyleManager.getOriginalStyle(switchToContactsButton) + "-fx-background-color: #ADD8E6;");
   }


    public SMSSceneControllerContainer getSceneControllerContainer() {
        return sceneControllerContainer;
    }

    public void setSceneControllerContainer(SMSSceneControllerContainer sceneControllerContainer) {
        this.sceneControllerContainer = sceneControllerContainer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
