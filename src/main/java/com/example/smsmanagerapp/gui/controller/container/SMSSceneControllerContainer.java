package com.example.smsmanagerapp.gui.controller.container;

import com.example.smsmanagerapp.gui.controller.CreateContactController;
import com.example.smsmanagerapp.gui.controller.HistorySMSMessagesController;
import com.example.smsmanagerapp.gui.controller.NewSMSMessagesController;
import com.example.smsmanagerapp.gui.controller.SendNewMessageController;
import javafx.scene.Scene;

public interface SMSSceneControllerContainer {
    public SendNewMessageController getSendNewMessageController();

    public void setSendNewMessageController(SendNewMessageController sendNewMessageController);
    public NewSMSMessagesController getNewSMSMessagesController();

    public void setNewSMSMessagesController(NewSMSMessagesController newSMSMessagesController);
    public HistorySMSMessagesController getHistorySMSMessagesController();
    public void setHistorySMSMessagesController(HistorySMSMessagesController historySMSMessagesController);
    public CreateContactController getCreateContactController();

    public void setCreateContactController(CreateContactController createContactController);

    void setNewSMSMessagesScene(Scene scene);

    void setHistorySMSMessagesScene(Scene scene);

    void setCreateContactScene(Scene scene);

    void setSendNewMessageScene(Scene scene);

    public Scene getNewSMSMessageScene();

    public Scene getHistorySMSMessagesScene();

    public Scene getCreateContactScene();

    public Scene getSendNewMessageScene();
}
