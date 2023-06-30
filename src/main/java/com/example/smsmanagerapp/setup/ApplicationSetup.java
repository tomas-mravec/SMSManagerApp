package com.example.smsmanagerapp.setup;

import com.example.smsmanagerapp.connection.ConnectionEstablisher;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.connection.database.MySQLDatabaseConnection;
import com.example.smsmanagerapp.gui.controller.container.SceneControllerContainerImpl;
import com.example.smsmanagerapp.table.manager.contact.ContactManager;
import com.example.smsmanagerapp.table.manager.contact.ContactManagerImpl;
import com.example.smsmanagerapp.table.manager.group.contact.GroupContactManagerImpl;
import com.example.smsmanagerapp.table.manager.message.SMSMessageManager;
import com.example.smsmanagerapp.factory.connection.ConnectionEstablisherFactory;
import com.example.smsmanagerapp.factory.listener.MessageListenerFactory;
import com.example.smsmanagerapp.gui.notifier.GUINotifier;
import com.example.smsmanagerapp.gui.controller.interfaces.GUIControllerUpdateable;
import com.example.smsmanagerapp.listener.MessageListener;
import com.example.smsmanagerapp.manager.MenuManager;
import com.example.smsmanagerapp.sender.MessageSender;
import com.example.smsmanagerapp.sender.MessageSenderYeastar;
import com.example.smsmanagerapp.table.manager.messageout.MessageOutImpl;
import com.example.smsmanagerapp.utility.DatabaseLoginData;
import javafx.scene.Scene;

import java.sql.Connection;

public class ApplicationSetup {

    private Scene scene;
    private GUIControllerUpdateable guiController;

    public ApplicationSetup(Scene scene, GUIControllerUpdateable guiController) {
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
//                DatabaseConnection databaseConnection = new MySQLDatabaseConnection(
//                DatabaseLoginData.getUrl(),
//                DatabaseLoginData.getUsername(),
//                DatabaseLoginData.getPassword());
//
//                Connection connection = databaseConnection.getConnection();
//                ContactManager contactManager = new ContactManagerImpl(connection);
//                SMSMessageManager messageManager = new SMSMessageManager(connection, contactManager);
//                MessageListener messageListener = new TestListener();
//                messageListener.addObserver(messageManager);
//
//                MenuManager.getMenuController().addMessageManager(messageManager);
//                MenuManager.getMenuController().setContactManager(contactManager);
//                guiController.addMessageManager(messageManager);
//                GUINotifier notifier = GUINotifier.getInstance();
//                notifier.setCurrentScene(scene);
//                notifier.setGuiController(guiController);
//                guiController.testReturn();
//                guiController.loadMessages();
//                messageListener.listenForMessages();



        final String DEVICE_IP_ADDRESS = "192.168.1.150";
        final int DEVICE_PORT = 5038;
        final String USERNAME = "apiuser";
        final String SECRET = "apipass";
        final String TYPE = "SMS_Yeastar";

        ConnectionEstablisher connectionEstablisher = ConnectionEstablisherFactory.create(TYPE, DEVICE_IP_ADDRESS,
                DEVICE_PORT,
                USERNAME,
                SECRET);

        connectionEstablisher.logIn();

        if (connectionEstablisher.getSocket().isPresent()) {


            DatabaseConnection databaseConnection = new MySQLDatabaseConnection(
                    DatabaseLoginData.getUrl(),
                    DatabaseLoginData.getUsername(),
                    DatabaseLoginData.getPassword());

            Connection connection = databaseConnection.getConnection();
            ContactManager contactManager = new ContactManagerImpl(connection);
            SMSMessageManager messageManager = new SMSMessageManager(connection, contactManager);
            MessageSender messageSender = new MessageSenderYeastar(connectionEstablisher.getOutputStream());


            MessageListener messageListener = MessageListenerFactory.create(TYPE,
                    connectionEstablisher.getSocket().get(),
                    connectionEstablisher.getOutputStream(),
                    connectionEstablisher.getInputStream());
            messageListener.addObserver(messageManager);

            MenuManager.getMenuController().addMessageManager(messageManager);
            MenuManager.getMenuController().setContactManager(contactManager);
            MenuManager.getMenuController().setMessageSender(messageSender);
            MenuManager.getMenuController().setConnection(connection);
            MenuManager.getMenuController().setMessageOutManager(new MessageOutImpl(connection));
            MenuManager.getMenuController().setGroupContactManager(new GroupContactManagerImpl(connection));

            SceneControllerContainerImpl sceneControllerContainer = new SceneControllerContainerImpl();
            setScenes(sceneControllerContainer);
            MenuManager.getMenuController().setSceneControllerContainer(sceneControllerContainer);

            //pri refactoringu vytvorit triedu od ktorej si bude menu pytat managerov

            guiController.addMessageManager(messageManager);
            GUINotifier notifier = GUINotifier.getInstance();
            notifier.setCurrentScene(scene);
            notifier.setGuiController(guiController);
            guiController.testReturn();
            guiController.loadMessages();
            messageListener.listenForMessages();

//            MessageListener messageListener = MessageListenerFactory.create(TYPE, connection.getSocket().get());
//            MessageManagerObserver messageContainer = new NewSMSMessageManager();
//            MessageManager messageManagerHistory = new HistorySMSMessageManager();
//            MenuManager.getMenuController().addMessageContainer(messageContainer);
//            MenuManager.getMenuController().addMessageContainer(messageManagerHistory);
//
//            guiController.addMessageContainer(messageContainer);
//            guiController.addMessageContainer(messageManagerHistory);
//            messageListener.addObserver(messageContainer);
//            GUINotifier notifier = GUINotifier.getInstance();
//            notifier.setCurrentScene(scene);
//            notifier.setGuiController(guiController);
//            messageListener.listenForMessages();
        } else {
            throw new RuntimeException();
        }
    }

    private void setScenes(SceneControllerContainerImpl sceneControllerContainer) {
        //newMessagesScene = fxmlLoad..............
        // sceneControllerCOntainer.setNewMessagesScene(newMessagesScene)
    }
}
