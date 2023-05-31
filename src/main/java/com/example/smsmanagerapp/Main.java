package com.example.smsmanagerapp;

import com.example.smsmanagerapp.connection.ConnectionEstablisher;
import com.example.smsmanagerapp.factory.connection.ConnectionEstablisherFactory;
import com.example.smsmanagerapp.factory.listener.MessageListenerFactory;
import com.example.smsmanagerapp.gui.GUIUpdater;
import com.example.smsmanagerapp.gui.GUIUpdaterImpl;
import com.example.smsmanagerapp.listener.MessageListener;
import com.example.smsmanagerapp.listener.test.TestListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new-sms-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SMSManager");
        stage.setScene(scene);
        stage.show();

        GUIUpdater smsMainGUIUpdater = new GUIUpdaterImpl();
        smsMainGUIUpdater.addGUIController(fxmlLoader.getController());
        MessageListener messageListener = new TestListener();
        messageListener.addObserver(smsMainGUIUpdater);
        messageListener.listenForMessages();

//        final String DEVICE_IP_ADDRESS = "192.168.1.150";
//        final int DEVICE_PORT = 5038;
//
//        final String USERNAME = "apiuser";
//        final String SECRET = "apipass";
//        final String TYPE = "SMS_Yeastar";
//
//
//        ConnectionEstablisher connection = ConnectionEstablisherFactory.create(TYPE, DEVICE_IP_ADDRESS,
//                DEVICE_PORT,
//                USERNAME,
//                SECRET);
//
//        connection.logIn();
//
//        if (connection.getSocket().isPresent()) {
//            MessageListener listener = MessageListenerFactory.create(TYPE, connection.getSocket().get());
//            //chceme dat do listenera(observable) observera(gui updater) a potom do gui updatera kontroler new sms message controller
//            GUIUpdater guiUpdater = new GUIUpdaterImpl();
//            guiUpdater.addGUIController(fxmlLoader.getController());
//            listener.addObserver(guiUpdater);
//            listener.listenForMessages();
//        } else {
//            throw new RuntimeException();
//        }
    }



    public static void main(String[] args) {
        launch();
//        final String DEVICE_IP_ADDRESS = "192.168.1.150";
//        final int DEVICE_PORT = 5038;
//
//        final String USERNAME = "apiuser";
//        final String SECRET = "apipass";
//        final String TYPE = "SMS_Yeastar";
//
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new-sms-view"));
//
//        ConnectionEstablisher connection = ConnectionEstablisherFactory.create(TYPE, DEVICE_IP_ADDRESS,
//                DEVICE_PORT,
//                USERNAME,
//                SECRET);
//
//        connection.logIn();
//
//        if (connection.getSocket().isPresent()) {
//            MessageListener listener = MessageListenerFactory.create(TYPE, connection.getSocket().get());
//            //chceme dat do listenera(observable) observera(gui updater) a potom do gui updatera kontroler new sms message controller
//            GUIUpdater guiUpdater = new GUIUpdaterImpl();
//            guiUpdater.addGUIController(fxmlLoader.getController());
//            listener.addObserver(guiUpdater);
//            listener.listenForMessages();
//        } else {
//            throw new RuntimeException();
//        }
    }
}