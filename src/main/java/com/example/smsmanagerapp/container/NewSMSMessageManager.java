package com.example.smsmanagerapp.container;

import com.example.smsmanagerapp.DatabaseLoginData;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.connection.database.MySQLDatabaseConnection;
import com.example.smsmanagerapp.container.interfaces.MessageManagerObserver;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewSMSMessageManager implements MessageManagerObserver {
    //private List<SMSMessage> smsMessages;
    private MessageRecencyType messageRecencyType;
    private DatabaseConnection databaseConnection;
    private Connection connection;
    private Statement statement;

    public NewSMSMessageManager() {
        messageRecencyType = MessageRecencyType.NEW_MESSAGE;
        //smsMessages = new ArrayList<>();
        databaseConnection = new MySQLDatabaseConnection(
                DatabaseLoginData.getUrl(),
                DatabaseLoginData.getUsername(),
                DatabaseLoginData.getPassword());
        connection = databaseConnection.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void newMessage(Data data) {
        handleNewMessage(data);
    }

    private void handleNewMessage(Data data) {
        //smsMessages.add((SMSMessage) data);
        SMSMessage smsMessage = (SMSMessage) data;
        String query = "INSERT INTO message (sender, content, seen) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,smsMessage.getSender());
            preparedStatement.setString(2, smsMessage.getContent());
            preparedStatement.setBoolean(3, smsMessage.isSeen());
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<? extends Data> getAllMessages() {
        //String query = "SELECT * FROM message";
        return List.of();
    }

    @Override
    public MessageRecencyType getContainerType() {
        return messageRecencyType;
    }

    @Override
    public void addMessage(Data data) {
        //smsMessages.add((SMSMessage) data);
    }

    @Override
    public List<? extends Data> getAllSeenMessages() {
        return null;
    }

    @Override
    public List<? extends Data> getAllNewMessages() {
        return null;
    }

    @Override
    public void setMessageAsSeen(Data data) {

    }

    @Override
    public void remove(Data data) {
        //smsMessages.remove(data);
    }
}
