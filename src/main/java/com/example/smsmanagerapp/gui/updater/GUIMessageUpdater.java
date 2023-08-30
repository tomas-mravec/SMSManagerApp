package com.example.smsmanagerapp.gui.updater;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.controller.interfaces.BlockController;
import com.example.smsmanagerapp.gui.controller.interfaces.DeletableMessagesController;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.gui.controller.message.MessageBlockController;
import com.example.smsmanagerapp.gui.updater.manager.DeleteMessagesManager;
import com.example.smsmanagerapp.gui.updater.manager.SetMessagesAsSeenManager;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class GUIMessageUpdater {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private HashMap<BlockController, SMSMessage> messages;
    private GUIControllerUpdateable controllerUpdateable;

    public GUIMessageUpdater(GUIControllerUpdateable guiControllerUpdateable) {
        this.controllerUpdateable = guiControllerUpdateable;
        this.messages = new HashMap<>();
    }

//    public MessageBlockController updateGUI(Data data, DeleteMessagesManager deleteMessagesManager,
//                                            SetMessagesAsSeenManager setMessagesAsSeenManager,
//                                            VBox messageBox) {
//        MessageBlockController messageBlockController;
//        if (data != null) {
//            Platform.runLater(() -> {
//                Parent parent;
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceHelper.getMessageBlockResource()));
//                //MessageBlockController messageBlockController;
//                try {
//                    parent = fxmlLoader.load();
//                    messageBlockController = fxmlLoader.getController();
//                    //setBlockController(messageBlockController);
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                SMSMessage smsMessage = (SMSMessage) data;
//
//                //messageBlockController.setDeletableMessageController(deletableMessagesController);
//                //messages.put(messageBlockController, smsMessage);
//                messageBlockController.setDeleteMessagesManager(deleteMessagesManager);
//                if (setMessagesAsSeenManager != null) {
//                    messageBlockController.setSetMessagesAsSeenManager(setMessagesAsSeenManager);
//                    messageBlockController.setMarkableAsSeen(true);
//                }
//                messageBlockController.setContactLabelText(smsMessage.getSender());
//                messageBlockController.setTimeLabelText(smsMessage.getRecvTime().format(formatter));
//                messageBlockController.setMessageLabelText(smsMessage.getContent());
//                Separator separator = new Separator();
//                messageBlockController.addSeparator(separator);
//                messages.put(messageBlockController, smsMessage);
//                controllerUpdateable.addMessageBlock(messageBlockController, smsMessage);
//
//                messageBox.getChildren().addAll(messageBlockController.getRoot(), separator);
//            });
//        }
//    }

    public HashMap<BlockController, SMSMessage> getMessages() {
        return messages;
    }

}
