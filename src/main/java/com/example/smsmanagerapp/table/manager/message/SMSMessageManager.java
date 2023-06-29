package com.example.smsmanagerapp.table.manager.message;

import com.example.smsmanagerapp.table.manager.contact.ContactManager;
import com.example.smsmanagerapp.data.contact.Contact;
import com.example.smsmanagerapp.table.manager.message.interfaces.MessageManager;
import com.example.smsmanagerapp.table.manager.type.MessageRecencyType;
import com.example.smsmanagerapp.connection.database.DatabaseConnection;
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
    private ContactManager contactManager;

    public SMSMessageManager(Connection connection, ContactManager contactManager) {
        this.connection = connection;
        this.contactManager = contactManager;
        messageRecencyType = MessageRecencyType.NEW_MESSAGE;
        //smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void newMessage(Data data) {
        saveNewContact(data);
        saveNewMessage(data);
    }

    private void saveNewContact(Data data) {
        SMSMessage smsMessage = (SMSMessage) data;
        //smsMessage.setContactId(contactManager.createNewContact(smsMessage.getSender()));
        //ulozi kontakt do databazy, dostane id a nastavi ho do kontaktu v sms sprave


        //if smsMessage.get sender, spytame sa databazy nech nam hodi kontakty s tymto cislom, ak nam pride viac ako 0 riadkov to znamena ze to cislo tam uz je a kontakt nejdeme uloziavat znova
            contactManager.createNewContact(smsMessage.getContact());
    }

    private void saveNewMessage(Data data) {
        //smsMessages.add((SMSMessage) data);
        SMSMessage smsMessage = (SMSMessage) data;
        String query = "INSERT INTO message (content, received_time, seen, sender) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //preparedStatement.setString(1,smsMessage.getSender());
            preparedStatement.setString(1, smsMessage.getContent());

            preparedStatement.setTimestamp(2, Timestamp.valueOf(smsMessage.getRecvTime()));
            preparedStatement.setBoolean(3, smsMessage.isSeen());
            preparedStatement.setString(4, smsMessage.getContact().getNumber());

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
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<? extends Data> getAllMessages() {
        String query = "SELECT * FROM message m LEFT JOIN contact c ON c.number = m.number ";
        Statement statement = null;
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt("m.id"));
                smsMessage.setContent(resultSet.getString("m.content"));
                smsMessage.setRecvTime(resultSet.getTimestamp("m.received_time").toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean("m.seen"));

                Contact contact = new Contact(resultSet.getString("c.number"));
                contact.setName(resultSet.getString("c.name"));
                smsMessage.setContact(contact);
                smsMessage.setSender(contact.getNumber());
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
        String query = "SELECT * FROM message m LEFT JOIN contact c ON c.number = m.sender WHERE m.seen = TRUE";
        Statement statement = null;
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt("m.id"));
                smsMessage.setContent(resultSet.getString("m.content"));
                smsMessage.setRecvTime(resultSet.getTimestamp("m.received_time").toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean("m.seen"));

                Contact contact = new Contact(resultSet.getString("c.number"));
                contact.setName(resultSet.getString("c.name"));
                smsMessage.setContact(contact);
                smsMessage.setSender(contact.getNumber());
                smsMessages.add(smsMessage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return smsMessages;
    }

    @Override
    public List<? extends Data> getAllNewMessages() {
        String query = "SELECT * FROM message m LEFT JOIN contact c ON c.number = m.sender WHERE m.seen = FALSE ";
        Statement statement = null;
        List<SMSMessage> smsMessages = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt("m.id"));
                smsMessage.setContent(resultSet.getString("m.content"));
                smsMessage.setRecvTime(resultSet.getTimestamp("m.received_time").toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean("m.seen"));

                Contact contact = new Contact(resultSet.getString("c.number"));
                contact.setName(resultSet.getString("c.name"));
                smsMessage.setContact(contact);
                smsMessage.setSender(contact.getNumber());
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

        String query = "SELECT * FROM message m LEFT JOIN contact c ON c.number = m.sender WHERE m.seen = ?";
       //String query = "SELECT * FROM message WHERE seen = ?";
        String filterQuery = "(m.sender LIKE CONCAT('%', ?, '%') OR c.name LIKE CONCAT('%', ?, '%'))";


        String dateFilterQuery = "DATE(m.received_time) = ?";
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
               preparedStatement.setString(3, filter);
            }
            if (isDateFiltered) {
                if (isStringFiltered) {
                    preparedStatement.setDate(4, Date.valueOf(dateFilter));
                } else {
                    preparedStatement.setDate(2, Date.valueOf(dateFilter));
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while  (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3));
                SMSMessage smsMessage = new SMSMessage();
                smsMessage.setId(resultSet.getInt("m.id"));
                smsMessage.setContent(resultSet.getString("m.content"));
                smsMessage.setRecvTime(resultSet.getTimestamp("m.received_time").toLocalDateTime());
                smsMessage.setSeen(resultSet.getBoolean("m.seen"));

                Contact contact = new Contact(resultSet.getString("c.number"));
                contact.setName(resultSet.getString("c.name"));
                smsMessage.setContact(contact);
                smsMessage.setSender(contact.getNumber());
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
                    smsMessage.setContent(resultSet.getString(2));
                    smsMessage.setRecvTime(resultSet.getTimestamp(3).toLocalDateTime());
                    smsMessage.setSeen(resultSet.getBoolean(4));

                    //pozreme sa do tabulky contact ci ma kontakt s tymto cislom aj meno, ak hej tak ho nacitame a
                    //ulozime do contact.setname() cize kontakty ktorym sme nastavili meno ho budu mat nastavene
                    //potom v kontroleroch kde zobrazujeme spravy tak tak ak ma contact.getName() tak vytvorime dalsi label pre to meno





                    Contact contact = new Contact(resultSet.getString(5));
                    smsMessage.setContact(contact);
                    smsMessage.setSender(contact.getNumber());
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
