package com.example.smsmanagerapp.table.manager.messageout;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.contact.Contact;
import com.example.smsmanagerapp.data.contact.GroupContact;
import com.example.smsmanagerapp.data.contact.SMSMessageOut;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MessageOutImpl implements MessageOutManager {

    private Connection connection;

    public MessageOutImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createNewOutMessage(String content, int groupContactId) {
        String sql = "INSERT INTO message_out (content, group_contact_id, time) VALUES (?,?,?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, content);
            preparedStatement.setInt(2, groupContactId);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(3, currentTime);
            preparedStatement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Number already saved");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Iterable<? extends Data> filterMessages(String receiverFilter, LocalDate dateFrom, LocalDate dateTo) {
        List<SMSMessageOut> messagesOut = new ArrayList<>();

        String sql = "SELECT DISTINCT g.id FROM group_contact g JOIN contact c ON g.number = c.number" +
                " WHERE (g.number LIKE CONCAT('%', ?, '%') OR c.name LIKE CONCAT('%', ?, '%'))"; //+
               //" GROUP BY g.id LIMIT 1";

        boolean dateFiltered = false;
        if (dateFrom != null && dateTo != null ) {
            dateFiltered = true;
            System.out.println("Date filtering = true");
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, receiverFilter);
            preparedStatement.setString(2, receiverFilter);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Group id je: " + resultSet.getInt(1));

                String sqlFindMessages = "SELECT * FROM message_out WHERE group_contact_id = ?";
                if (dateFiltered) {
                    sqlFindMessages += " AND (DATE(time) BETWEEN ? AND ?)";
                    System.out.println("Pridavam sql");
                }
                preparedStatement = connection.prepareStatement(sqlFindMessages);
                preparedStatement.setInt(1, resultSet.getInt(1));
                if (dateFiltered) {
                    preparedStatement.setDate(2, Date.valueOf(dateFrom));
                    preparedStatement.setDate(3, Date.valueOf(dateTo));
                    System.out.println("Pridavam parametre");
                }
                ResultSet messagesSet = preparedStatement.executeQuery();

                while (messagesSet.next()) {
                    SMSMessageOut smsMessageOut = new SMSMessageOut();
                    smsMessageOut.setId(messagesSet.getInt("id"));
                    smsMessageOut.setContent(messagesSet.getString("content"));
                    smsMessageOut.setTime(messagesSet.getTimestamp("time").toLocalDateTime());
                    System.out.println("Content je: " + smsMessageOut.getContent());

                    String sqlFindContacts = "SELECT g.number, c.name FROM group_contact g" +
                            " JOIN contact c ON g.number = c.number" +
                            " WHERE g.id = ?";
                    preparedStatement = connection.prepareStatement(sqlFindContacts);
                    preparedStatement.setInt(1, resultSet.getInt(1));
                    ResultSet contactSet = preparedStatement.executeQuery();

                    GroupContact groupContact = new GroupContact();
                    while (contactSet.next()) {
                        System.out.println("Kontakt: " + contactSet.getString("g.number"));
                        Contact contact = new Contact(contactSet.getString("g.number"));
                        contact.setName(contactSet.getString("c.name"));
                        groupContact.addContact(contact);
                    }
                    smsMessageOut.setGroupContact(groupContact);
                    messagesOut.add(smsMessageOut);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return messagesOut;
    }

    @Override
    public Iterable<? extends Data> getAllMessages() {
        List<SMSMessageOut> messages = new ArrayList<>();
        String sql = "SELECT * FROM message_out";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);;
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                SMSMessageOut messageOut = new SMSMessageOut();
                messageOut.setId(resultSet.getInt(1));
                messageOut.setContent(resultSet.getString(2));
                messageOut.setTime(resultSet.getTimestamp(4).toLocalDateTime());
                GroupContact groupContact = new GroupContact();

                String sql2 = "SELECT g.number, c.name FROM group_contact g " +
                        "JOIN contact c ON g.number = c.number " +
                        "WHERE g.id = ? ";
                       ;
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1,resultSet.getInt(3));
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while(resultSet2.next()) {
                    Contact contact = new Contact(resultSet2.getString(1));
                    contact.setName(resultSet2.getString(2));
                    groupContact.addContact(contact);
                }
                messageOut.setGroupContact(groupContact);
                messages.add(messageOut);
           }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Number already saved");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return messages;
    }

    @Override
    public void deleteMessage(int id) {
        System.out.println("Sprava na vymazanie ma id: " + id);
        String sql = "DELETE FROM message_out WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            int resultSet = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
