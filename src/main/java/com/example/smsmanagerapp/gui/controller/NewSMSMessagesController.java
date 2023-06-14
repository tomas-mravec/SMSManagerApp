package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.manager.MenuManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewSMSMessagesController implements GUIController, Initializable {

    @FXML
    private VBox messageBox;

    private VBox menu;
    private MenuControllerImpl menuController;
    @FXML
    private AnchorPane rootPane;
    private List<MessageManager> messageManagers;
    private MessageRecencyType recencyType;

    public NewSMSMessagesController() {
        messageManagers = new ArrayList<>();
        recencyType = MessageRecencyType.NEW_MESSAGE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Loads menu.fxml, then loads MenuBox root node into menuBox variable which is then set as child of rootPane

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smsmanagerapp/menu.fxml"));
//        try {
//            menu = fxmlLoader.load(); // Assign the loaded value to menu
//            menuController = fxmlLoader.getController(); //assign controller of menu
//
//            //tak nakoniec loader vytvori dve instancie, jednu ako root node co reprezentuje menu box, a dalsi menu box co je controller
//
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }

        //menuBox = MenuBox.getInstance();
        menu = MenuManager.getMenuInstance();
        menuController = MenuManager.getMenuController();
        rootPane.getChildren().add(0, menu); // Add menuBox as the first child
    }




    // Urobime SMSMessage singleton a mozeme ho akcessnut hocikde, a vzdy ked vytvorime novy kontroler tak si ho vypytame
    //mozeme urobit novy sms container kde budu ulozene nove spravy a ten kontainer nacitame do new sms messages controller hmmmmm









//    private void addMessageContainerToMenu(MessageContainer messageContainer) {
//        System.out.println("GUIController pridava message container do menu");
//        menuController.addMessageContainer(messageContainer);
//    }

    public void loadMessages() {
        System.out.println("Loadujem data z gui controllera");
        for (MessageManager messageManager : messageManagers) {
           for (Data data : messageManager.getAllNewMessages()) {
                   updateGUI(data);
           }
        }
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


                Button sendToHistory = new Button();
                sendToHistory.setText("Videné");
                sendToHistory.setOnAction(event -> {
                    System.out.println("Bol stlaceny button na odoslanie do historie");
                    sendMessageToHistory(smsMessage);
                    messageBox.getChildren().remove(messageContainer);
                });

                Separator separator = new Separator();
                separator.getStyleClass().add("\\css\\message-separator");

                messageContainer.getChildren().addAll(label1, label3, sendToHistory, separator);

//        messageContainer.setMaxWidth(Double.MAX_VALUE);
//        messageBox.setMaxWidth(Double.MAX_VALUE);
//        VBox.setVgrow(messageBox, Priority.ALWAYS);
//        VBox.setVgrow(messageContainer, Priority.ALWAYS);

                messageBox.getChildren().add(messageContainer);

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

    public void testReturn() {
        System.out.println("GUIController sa ozyva " + messageManagers.get(0).toString());
    }

    public MessageRecencyType getRecencyType() {
        return recencyType;
    }
}