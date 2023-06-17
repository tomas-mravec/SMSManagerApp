package com.example.smsmanagerapp.setup;

import com.example.smsmanagerapp.connection.ConnectionEstablisher;
import com.example.smsmanagerapp.container.SMSMessageManager;
import com.example.smsmanagerapp.factory.connection.ConnectionEstablisherFactory;
import com.example.smsmanagerapp.factory.listener.MessageListenerFactory;
import com.example.smsmanagerapp.gui.GUINotifier;
import com.example.smsmanagerapp.gui.controller.GUIController;
import com.example.smsmanagerapp.listener.MessageListener;
import com.example.smsmanagerapp.listener.test.TestListener;
import com.example.smsmanagerapp.manager.MenuManager;
import javafx.scene.Scene;

public class ApplicationSetup {

    private Scene scene;
    private GUIController guiController;

    public ApplicationSetup(Scene scene, GUIController guiController) {
        this.scene = scene;
        this.guiController = guiController;
    }

    public void setUp() {
//            GUIUpdater smsMainGUIUpdater = new GUIUpdaterImpl();
//            smsMainGUIUpdater.addGUIController(fxmlLoader.getController());
//            MessageListener messageListener = new TestListener();
//            messageListener.addObserver(smsMainGUIUpdater);
//            messageListener.listenForMessages();
//
//             MessageContainerObserver messageContainer = new NewSMSMessageContainer();
//             MessageContainer messageContainerHistory = new HistorySMSMessageContainer();
//             MenuManager.getMenuController().addMessageContainer(messageContainer);
//             MenuManager.getMenuController().addMessageContainer(messageContainerHistory);
//             MessageListener messageListener = new TestListener();
//
//             GUINotifier notifier = GUINotifier.getInstance();
//             guiController.addMessageContainer(messageContainer);
//             guiController.addMessageContainer(messageContainerHistory);
//             notifier.setCurrentScene(scene);
//             notifier.setGuiController(guiController);
//             messageListener.addObserver(messageContainer);
//             guiController.testReturn();
//             messageListener.listenForMessages();
//
                SMSMessageManager messageManager = new SMSMessageManager();
                MessageListener messageListener = new TestListener();
                messageListener.addObserver(messageManager);
                MenuManager.getMenuController().addMessageManager(messageManager);
                guiController.addMessageManager(messageManager);
                GUINotifier notifier = GUINotifier.getInstance();
                notifier.setCurrentScene(scene);
                notifier.setGuiController(guiController);
                guiController.testReturn();
                guiController.loadMessages();
                messageListener.listenForMessages();



//        final String DEVICE_IP_ADDRESS = "192.168.1.150";
//        final int DEVICE_PORT = 5038;
//        final String USERNAME = "apiuser";
//        final String SECRET = "apipass";
//        final String TYPE = "SMS_Yeastar";
//
//        ConnectionEstablisher connection = ConnectionEstablisherFactory.create(TYPE, DEVICE_IP_ADDRESS,
//                DEVICE_PORT,
//                USERNAME,
//                SECRET);
//
//        connection.logIn();
//
//        if (connection.getSocket().isPresent()) {
//
//
//            SMSMessageManager messageManager = new SMSMessageManager();
//            MessageListener messageListener = MessageListenerFactory.create(TYPE, connection.getSocket().get());
//            messageListener.addObserver(messageManager);
//            MenuManager.getMenuController().addMessageManager(messageManager);
//            guiController.addMessageManager(messageManager);
//            GUINotifier notifier = GUINotifier.getInstance();
//            notifier.setCurrentScene(scene);
//            notifier.setGuiController(guiController);
//            guiController.testReturn();
//            guiController.loadMessages();
//            messageListener.listenForMessages();


//            MessageListener listener = MessageListenerFactory.create(TYPE, connection.getSocket().get());
//            MessageManagerObserver messageContainer = new NewSMSMessageManager();
//            MessageManager messageManagerHistory = new HistorySMSMessageManager();
//            MenuManager.getMenuController().addMessageContainer(messageContainer);
//            MenuManager.getMenuController().addMessageContainer(messageManagerHistory);
//
//            guiController.addMessageContainer(messageContainer);
//            guiController.addMessageContainer(messageManagerHistory);
//            listener.addObserver(messageContainer);
//            GUINotifier notifier = GUINotifier.getInstance();
//            notifier.setCurrentScene(scene);
//            notifier.setGuiController(guiController);
//            listener.listenForMessages();
       // } else {
        //    throw new RuntimeException();
        //}
    }
}
