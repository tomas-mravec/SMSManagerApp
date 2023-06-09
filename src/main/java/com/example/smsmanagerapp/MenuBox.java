package com.example.smsmanagerapp;

import com.example.smsmanagerapp.container.interfaces.MessageContainer;
import com.example.smsmanagerapp.gui.controller.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuBox extends VBox{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private List<MessageContainer> messageContainers;

    @FXML
    private Button switchToNewMessages;

    private List<Integer> intList;

    public MenuBox() {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smsmanagerapp/menu.fxml"));
//        //fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);

//        try {
//            fxmlLoader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//        switchToNewMessages.setOnAction(event -> {
//            try {
//                switchToNewSMSScene(event);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
        //historyButton.setOnAction(event -> switchToHistoryScene());
        messageContainers = new ArrayList<>();
        intList = new ArrayList<>();
        Random random = new Random();
        int randomNumber = random.nextInt(21);
        for (int i = 0; i < randomNumber; i++) {
            intList.add(i);
        }
    }

    @FXML
    private void initialize() {
    }

    /// skus dat random generator a nech menubox vypise to random cislo ci to je stale ta ista instancia a nie ina
   // je to confirmed je to nova instancia, treba zistit preco a da sa to vyriesit implementaciou singletona
    public void switchToNewSMSScene(ActionEvent event) throws IOException {
        System.out.println("Stlacil som tlacidlo, length array je: " + this.messageContainers.size());
        System.out.println("Stlacil som tlacidlo, int list ma length: " + intList.size());
       // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-sms-view.fxml"));
//        root = fxmlLoader.load();
//        GUIController guiController = fxmlLoader.getController();
        //addMessageContainersToController(guiController);
        System.out.println("Stlacil som button, nizsie bude container controllera: ");
        //guiController.testReturn();
        //scene = new Scene(root);
        //GUINotifier notifier = GUINotifier.getInstance();
        //notifier.setCurrentScene(scene);
        //notifier.setGuiController(guiController);
        //stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //stage.setScene(scene);
        //guiController.loadMessages();
        //stage.show();
    }

    private void addMessageContainersToController(GUIController guiController) {
        System.out.println("Dam vobec nejaky kontainer do controllera?: " + "Dlzka menu: " + this.toString() + " array kontainerov je " + messageContainers.size());
        for (MessageContainer messageContainer: messageContainers) {
            System.out.println("YEP");
            guiController.addMessageContainer(messageContainer);
        }
    }


    //vytvor kontainery pre messages kde sa budu uloziavat messages, tieto kontajneri budu observers listenera a nie gui updater, ked pride nova sprava, staci aby bol GUI updater len jeden
    // lebo aktualna scena je len jedna a mozes sa spytat ze ci je instance of napr UpdateableSMSMessage a ak hej tak mu data posli ak nie tak mu neopsielaj


    public void switchToHistoryScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("history-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addMessageContainer(MessageContainer messageContainer) {
        this.messageContainers.add(messageContainer);
        System.out.println("Menu: " + this.toString() +" message container received" + " length of container array is: " + messageContainers.size());
        System.out.println("Intlist ma dlzku: " + intList.size());
    }
}
