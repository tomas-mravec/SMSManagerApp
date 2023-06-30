package com.example.smsmanagerapp.table.manager.messageout;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.contact.Contact;
import com.example.smsmanagerapp.data.contact.GroupContact;
import com.example.smsmanagerapp.data.contact.SMSMessageOut;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.*;

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
}
