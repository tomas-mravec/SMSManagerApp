package com.example.smsmanagerapp.gui.controller;

import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.gui.GUINotifier;
import com.example.smsmanagerapp.setup.NewSceneSetup;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuControllerImpl {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private List<MessageManager> messageManagers;

    @FXML
    private Button switchToNewMessages;

    private List<Integer> intList;

    public MenuControllerImpl() {
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
        messageManagers = new ArrayList<>();
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
        System.out.println("Stlacil som tlacidlo, length array je: " + this.messageManagers.size());
        System.out.println("Stlacil som tlacidlo, int list ma length: " + intList.size());

        NewSceneSetup newSceneSetup = new NewSceneSetup(stage, ResourceHelper.getNewSMSSceneResource(), messageManagers);
        newSceneSetup.switchToNewScene();
    }

    private void addMessageManagersToController(GUIController guiController) {
        System.out.println("Dam vobec nejaky kontainer do controllera?: " + "Dlzka menu: " + this.toString() + " array kontainerov je " + messageManagers.size());
        for (MessageManager messageManager : messageManagers) {
                guiController.addMessageManager(messageManager);
                System.out.println("YEP");
        }
    }


    //vytvor kontainery pre messages kde sa budu uloziavat messages, tieto kontajneri budu observers listenera a nie gui updater, ked pride nova sprava, staci aby bol GUI updater len jeden
    // lebo aktualna scena je len jedna a mozes sa spytat ze ci je instance of napr UpdateableSMSMessage a ak hej tak mu data posli ak nie tak mu neopsielaj


    public void switchToHistoryScene(ActionEvent event) throws IOException {
        System.out.println("Stlacil som tlacidlo, length array je: " + this.messageManagers.size());
        System.out.println("Stlacil som tlacidlo, int list ma length: " + intList.size());

        NewSceneSetup newSceneSetup = new NewSceneSetup(stage, ResourceHelper.getHistorySMSSceneResource(), messageManagers);
        newSceneSetup.switchToNewScene();
    }

    public void addMessageManager(MessageManager messageManager) {
        this.messageManagers.add(messageManager);
        System.out.println("Menu: " + this.toString() +" message container received" + " length of container array is: " + messageManagers.size());
        System.out.println("Intlist ma dlzku: " + intList.size());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
