package com.example.smsmanagerapp.container;

import com.example.smsmanagerapp.DatabaseLoginData;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.connection.database.MySQLDatabaseConnection;
import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.interfaces.MessageManagerObserver;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.GUINotifier;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SMSMessageManager implements MessageManager, IMessageListenerObserver {
    private MessageRecencyType messageRecencyType;
    private DatabaseConnection databaseConnection;
    private Connection connection;
    private Statement statement;

    public SMSMessageManager() {
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
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,smsMessage.getSender());
            preparedStatement.setString(2, smsMessage.getContent());
            preparedStatement.setBoolean(3, smsMessage.isSeen());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    smsMessage.setId(generatedId);
                    GUINotifier.getInstance().newMessage(smsMessage);
                }
                generatedKeys.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<? extends Data> getAllMessages() {
        String query = "SELECT * FROM message";
        Statement statement = null;
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt(1));
                smsMessage.setSender(resultSet.getString(2));
                smsMessage.setContent(resultSet.getString(3));
                smsMessage.setSeen(resultSet.getBoolean(4));
                smsMessages.add(smsMessage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return smsMessages;
    }

    @Override
    public MessageRecencyType getContainerType() {
        return messageRecencyType;
    }

    @Override
    public void addMessage(Data data) {
       // smsMessages.add((SMSMessage) data);
    }

    @Override
    public List<? extends Data> getAllSeenMessages() {
        String query = "SELECT * FROM message WHERE seen = TRUE";
        Statement statement = null;
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt(1));
                smsMessage.setSender(resultSet.getString(2));
                smsMessage.setContent(resultSet.getString(3));
                smsMessage.setSeen(resultSet.getBoolean(4));
                smsMessages.add(smsMessage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return smsMessages;
    }

    @Override
    public List<? extends Data> getAllNewMessages() {
        String query = "SELECT * FROM message WHERE seen = FALSE";
        Statement statement = null;
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt(1));
                smsMessage.setSender(resultSet.getString(2));
                smsMessage.setContent(resultSet.getString(3));
                smsMessage.setSeen(resultSet.getBoolean(4));
                smsMessages.add(smsMessage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return smsMessages;
    }

    @Override
    public void setMessageAsSeen(Data data) {
        SMSMessage smsMessage = (SMSMessage) data;
        System.out.println("Nastavujem sms message as seen s id " + smsMessage.getId());
        int id = smsMessage.getId();
        String query = "UPDATE message SET seen = true WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Data data) {
       // smsMessages.remove(data);
    }
}
