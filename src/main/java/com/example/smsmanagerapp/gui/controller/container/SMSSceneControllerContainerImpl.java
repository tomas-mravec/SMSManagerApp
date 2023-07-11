package com.example.smsmanagerapp.gui.controller.container;

import com.example.smsmanagerapp.gui.controller.contact.CreateContactController;
import com.example.smsmanagerapp.gui.controller.message.HistorySMSMessagesController;
import com.example.smsmanagerapp.gui.controller.message.NewSMSMessagesController;
import com.example.smsmanagerapp.gui.controller.send.SendNewMessageController;
import javafx.scene.Scene;

public class SMSSceneControllerContainerImpl implements SMSSceneControllerContainer {

    private SendNewMessageController sendNewMessageController;
    private NewSMSMessagesController newSMSMessagesController;
    private HistorySMSMessagesController historySMSMessagesController;
    private CreateContactController createContactController;

    private Scene newSMSMessageScene;
    private Scene historySMSMessagesScene;
    private Scene createContactScene;
    private Scene sendNewMessageScene;

    public SendNewMessageController getSendNewMessageController() {
        return sendNewMessageController;
    }

    public void setSendNewMessageController(SendNewMessageController sendNewMessageController) {
        this.sendNewMessageController = sendNewMessageController;
    }

    public NewSMSMessagesController getNewSMSMessagesController() {
        return newSMSMessagesController;
    }

    public void setNewSMSMessagesController(NewSMSMessagesController newSMSMessagesController) {
        this.newSMSMessagesController = newSMSMessagesController;
    }

    public HistorySMSMessagesController getHistorySMSMessagesController() {
        return historySMSMessagesController;
    }

    public void setHistorySMSMessagesController(HistorySMSMessagesController historySMSMessagesController) {
        this.historySMSMessagesController = historySMSMessagesController;
    }

    public CreateContactController getCreateContactController() {
        return createContactController;
    }

    public void setCreateContactController(CreateContactController createContactController) {
        this.createContactController = createContactController;
    }

    @Override
    public void setNewSMSMessagesScene(Scene scene) {
        this.newSMSMessageScene = scene;
    }

    @Override
    public void setHistorySMSMessagesScene(Scene scene) {
        this.historySMSMessagesScene = scene;
    }

    @Override
    public void setCreateContactScene(Scene scene) {
        this.createContactScene = scene;
    }


    @Override
    public void setSendNewMessageScene(Scene scene) {
        this.sendNewMessageScene = scene;
    }

    public Scene getNewSMSMessageScene() {
        return newSMSMessageScene;
    }

    public Scene getHistorySMSMessagesScene() {
        return historySMSMessagesScene;
    }

    public Scene getCreateContactScene() {
        return createContactScene;
    }

    public Scene getSendNewMessageScene() {
        return sendNewMessageScene;
    }
}
