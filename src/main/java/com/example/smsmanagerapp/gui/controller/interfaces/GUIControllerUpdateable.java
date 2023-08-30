package com.example.smsmanagerapp.gui.controller.interfaces;

import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIController;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.gui.controller.message.MessageBlockController;
import javafx.scene.Parent;

public interface GUIControllerUpdateable extends GUIController {
    public void updateGUI(Data data);

    public void addMessageBlock(MessageBlockController controller, SMSMessage smsMessage);
//    public void markAsSeen(Parent parent, boolean checked);
//
//    public void unMarkAsSeen(Parent parent);

}
