package com.example.smsmanagerapp.setup;

import com.example.smsmanagerapp.connection.ConnectionEstablisher;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.connection.database.MySQLDatabaseConnection;
import com.example.smsmanagerapp.gui.controller.contact.CreateContactController;
import com.example.smsmanagerapp.gui.controller.message.HistorySMSMessagesController;
import com.example.smsmanagerapp.gui.controller.message.NewSMSMessagesController;
import com.example.smsmanagerapp.gui.controller.send.SendNewMessageController;
import com.example.smsmanagerapp.gui.controller.container.SMSSceneControllerContainer;
import com.example.smsmanagerapp.gui.controller.container.SMSSceneControllerContainerImpl;
import com.example.smsmanagerapp.table.manager.contact.ContactManager;
import com.example.smsmanagerapp.table.manager.contact.ContactManagerImpl;
import com.example.smsmanagerapp.table.manager.group.contact.GroupContactManager;
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
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.messageout.MessageOutImpl;
import com.example.smsmanagerapp.table.manager.messageout.MessageOutManager;
import com.example.smsmanagerapp.utility.DatabaseLoginData;
import com.example.smsmanagerapp.utility.ResourceHelper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class ApplicationSetup {

    private Scene scene;
    private GUIControllerUpdateable guiController;
    private Stage stage;
    private SMSSceneControllerContainer sceneControllerContainer;

    public ApplicationSetup(Stage stage) {
        this.scene = scene;
        this.guiController = guiController;
        this.stage = stage;
        stage.setResizable(false);
        this.sceneControllerContainer = new SMSSceneControllerContainerImpl();
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

       // if (connectionEstablisher.getSocket().isPresent()) {

            DatabaseConnection databaseConnection = new MySQLDatabaseConnection(
                    DatabaseLoginData.getUrl(),
                    DatabaseLoginData.getUsername(),
                    DatabaseLoginData.getPassword());

            Connection connection = databaseConnection.getConnection();
            ContactManager contactManager = new ContactManagerImpl(connection);
            SMSMessageManager messageManager = new SMSMessageManager(connection, contactManager);
            MessageSender messageSender = new MessageSenderYeastar(connectionEstablisher.getOutputStream());
            MessageOutImpl messageOutManager = new MessageOutImpl(connection);
            GroupContactManagerImpl groupContactManager = new GroupContactManagerImpl(connection);

//            MessageListener messageListener = MessageListenerFactory.create(TYPE,
//                   // connectionEstablisher.getSocket().get(),
//                    null,
//                    connectionEstablisher.getOutputStream(),
//                    connectionEstablisher.getInputStream());
//            messageListener.addObserver(messageManager);

            MenuManager.getMenuController().setSceneControllerContainer(sceneControllerContainer);

            try {
                createScenesControllers(messageManager, contactManager, messageOutManager, messageSender, groupContactManager); //using new scene setup
            } catch (IOException e) {
                System.out.println("KEK WHAT A KWAB");
                throw new RuntimeException(e);
            }

            GUINotifier notifier = GUINotifier.getInstance();
            notifier.setCurrentScene(sceneControllerContainer.getNewSMSMessageScene());
            NewSMSMessagesController newSMSMessagesController = sceneControllerContainer.getNewSMSMessagesController();
            newSMSMessagesController.setMenu();
            notifier.setGuiController(newSMSMessagesController);
            stage.setScene(sceneControllerContainer.getNewSMSMessageScene());
            stage.show();
         //   newSMSMessagesController.testReturn();
           // newSMSMessagesController.loadMessages();
           // messageListener.listenForMessages();

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
//        } else {
//            System.out.printf("heh");
//            throw new RuntimeException();
//        }
    }

    private void createScenesControllers(MessageManager messageManager, ContactManager contactManager,
                                                  MessageOutManager messageOutManager,
                                                  MessageSender messageSender,
                                                  GroupContactManager groupContactManager) throws IOException {


        NewSceneSetup newSMSSceneSetup = new NewSceneSetup(stage, ResourceHelper.getNewSMSSceneResource(), List.of(messageManager));
        NewSMSMessagesController newSMSMessagesController = null;
        newSMSMessagesController = (NewSMSMessagesController) newSMSSceneSetup.loadController();
        sceneControllerContainer.setNewSMSMessagesController(newSMSMessagesController);
        sceneControllerContainer.setNewSMSMessagesScene(new Scene(newSMSMessagesController.getRoot()));
        newSMSMessagesController.testReturn();
        newSMSMessagesController.loadMessages();


         NewSceneSetup historySceneSetup = new NewSceneSetup(stage, ResourceHelper.getHistorySMSSceneResource(), List.of(messageManager));
         HistorySMSMessagesController historySMSMessagesController = null;
         historySMSMessagesController = (HistorySMSMessagesController) historySceneSetup.loadController();
         sceneControllerContainer.setHistorySMSMessagesController(historySMSMessagesController);
         Parent rootPaneParent = historySMSMessagesController.getRoot();
         sceneControllerContainer.setHistorySMSMessagesScene(new Scene(rootPaneParent));
         historySMSMessagesController.loadMessages();



        NewSceneSetup createContactSceneSetup = new NewSceneSetup(stage, ResourceHelper.getCreateContactSceneResource(), null);
        CreateContactController createContactController = (CreateContactController) createContactSceneSetup.loadController();
        createContactController.setContactManager(contactManager);
        sceneControllerContainer.setCreateContactController(createContactController);
        sceneControllerContainer.setCreateContactScene(new Scene(createContactController.getRoot()));
        createContactController.loadMessages();

        NewSceneSetup sendNewMessageSceneSetup = new NewSceneSetup(stage, ResourceHelper.getSendNewMessageSceneResource(), null);
        SendNewMessageController sendNewMessageController = (SendNewMessageController) sendNewMessageSceneSetup.loadController();
        sendNewMessageController.setMessageOutManager(messageOutManager);
        sendNewMessageController.setMessageSender(messageSender);
        sendNewMessageController.setContactManager(contactManager);
        sendNewMessageController.setGroupContactManager(groupContactManager);
        sceneControllerContainer.setSendNewMessageController(sendNewMessageController);
        sceneControllerContainer.setSendNewMessageScene(new Scene(sendNewMessageController.getRoot()));
        sendNewMessageController.loadMessages();

   // });
    }

//    private void setScenes(SceneControllerContainerImpl sceneControllerContainer) {
//        //newMessagesScene = fxmlLoad..............
//        // sceneControllerCOntainer.setNewMessagesScene(newMessagesScene)
//    }
}
