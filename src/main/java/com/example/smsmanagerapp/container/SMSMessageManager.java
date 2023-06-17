package com.example.smsmanagerapp.container;

import com.example.smsmanagerapp.utility.DatabaseLoginData;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.connection.database.MySQLDatabaseConnection;
import com.example.smsmanagerapp.container.interfaces.MessageManager;
import com.example.smsmanagerapp.container.type.MessageRecencyType;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.GUINotifier;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;

import java.sql.*;
import java.time.LocalDate;
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
        String query = "INSERT INTO message (sender, content, received_time, seen) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,smsMessage.getSender());
            preparedStatement.setString(2, smsMessage.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(smsMessage.getRecvTime()));
            preparedStatement.setBoolean(4, smsMessage.isSeen());

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
                smsMessage.setRecvTime(resultSet.getTimestamp(4).toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean(5));
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
                smsMessage.setRecvTime(resultSet.getTimestamp(4).toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean(5));
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
                smsMessage.setRecvTime(resultSet.getTimestamp(4).toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean(5));
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
    public List<? extends Data> getFilteredMessages(String filter, LocalDate dateFilter) {
        boolean isStringFiltered = false;
        boolean isDateFiltered = false;
        String query = "SELECT * FROM message WHERE seen = ?";
        String filterQuery = "sender LIKE CONCAT('%', ?, '%')";
        //sender LIKE alebo (OR) contact_id.name LIKE ...
        //potrebujeme vytvorit novu scenu kde nastavime vsetkym messages contact s prislusnym id contactu ktory user vytvori v tejto scene
        //vytvorime novy label kde sa zobrazi conatct name ak contact k tomu cislu existuje
        //po kliknuti buttona zobereme cislo a nazov z policok v novej scene, insertneme to do tabulky conctact ako novy row
        // potom ked insertneme tak zobereme primarny kluc z prepared statementu a hodime ho do vsetkych messages kde sa sender rovna cislu ktore uzivatel zadal do textfieldu
        // takze teraz by mali mat vsetky messages ktore poslal dany sender mat aj foreign key co je id v contact a pomocou neho sa mozeme dostat k menu toho sendera
        // potom aj bude treba osetrit ze nemozeme message priradit foreign key noveho kontatku ak uz tam niekoho ma
        //nezabudni ak bude treba vyuzit joiny




        String dateFilterQuery = "DATE(received_time) = ?";
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            if (filter != null && !filter.isEmpty()) {
                query += " AND " + filterQuery;
                isStringFiltered = true;
            }
            if (dateFilter != null) {
                query += " AND " + dateFilterQuery;
                isDateFiltered = true;
            }
            System.out.println("Query is: " + query);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, true);
            if (isStringFiltered) {
               preparedStatement.setString(2, filter);
            }
            if (isDateFiltered) {
                if (isStringFiltered) {
                    preparedStatement.setDate(3, Date.valueOf(dateFilter));
                } else {
                    preparedStatement.setDate(2, Date.valueOf(dateFilter));
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt(1));
                smsMessage.setSender(resultSet.getString(2));
                smsMessage.setContent(resultSet.getString(3));
                smsMessage.setRecvTime(resultSet.getTimestamp(4).toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean(5));
                smsMessages.add(smsMessage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return smsMessages;
    }

    @Override
    public List<? extends Data> getAllSeenByDate(LocalDate date) {
        List<SMSMessage> smsMessages = new ArrayList<>();
        if (date != null) {
            try {
                String sql = "SELECT * FROM message WHERE DATE(received_time) = ? AND seen = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDate(1, Date.valueOf(date));
                statement.setBoolean(2, true);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                    SMSMessage smsMessage = new SMSMessage();
                    smsMessage.setId(resultSet.getInt(1));
                    smsMessage.setSender(resultSet.getString(2));
                    smsMessage.setContent(resultSet.getString(3));
                    smsMessage.setRecvTime(resultSet.getTimestamp(4).toLocalDateTime());
                    smsMessage.setSeen(resultSet.getBoolean(5));
                    smsMessages.add(smsMessage);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return smsMessages;
    }


    @Override
    public void remove(Data data) {
       // smsMessages.remove(data);
    }
}
