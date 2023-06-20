package com.example.smsmanagerapp.garbage;

import com.example.smsmanagerapp.utility.DatabaseLoginData;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.connection.database.MySQLDatabaseConnection;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;


import java.sql.*;
import java.util.List;

public class NewSMSMessageManager  {
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

    public List<? extends Data> getAllMessages() {
        //String query = "SELECT * FROM message";
        return List.of();
    }

    public MessageRecencyType getContainerType() {
        return messageRecencyType;
    }

    public List<? extends Data> getAllSeenMessages() {
        return null;
    }

    public List<? extends Data> getAllNewMessages() {
        return null;
    }

    public void setMessageAsSeen(Data data) {

    }

    public List<? extends Data> getFilteredMessages(String filter) {
        return null;
    }

    public void remove(Data data) {
        //smsMessages.remove(data);
    }
}
