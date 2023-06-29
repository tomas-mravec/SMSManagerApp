package com.example.smsmanagerapp.table.manager.contact;

import com.example.smsmanagerapp.data.contact.Contact;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactManagerImpl implements ContactManager {

    private Connection connection;
    private Statement statement;


    public ContactManagerImpl(Connection connection) {
        this.connection = connection;
    }

//    public void setConnection(Connection connection) {
//        this.connection = connection;
//    }

    @Override
    public boolean createNewContact(Contact contact) {
        String sql = "INSERT INTO contact (number) VALUES (?)";
        boolean newContactCreated = false;
        try {
            //predtym by sa hodilo skontrolovat ci sa tel cislo uz v tabulke contact nachadza a ak hej tak pozret ci nema priradene meno a priradit ho aj kontaktu v sms sprave
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contact.getNumber());
            preparedStatement.executeUpdate();
            newContactCreated = true;
            System.out.println("Novy kontakt bol vytvoreny s cislom " + contact.getNumber());

//            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected == 1) {
//                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                     generatedId = generatedKeys.getInt(1);
//                }
//                generatedKeys.close();
//            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Number already saved");
            newContactCreated = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newContactCreated;
    }

    @Override
    public Optional<Contact> findContact(String identifier) {
        Contact contact = null;
        String sql = "SELECT * FROM contact WHERE number = ? OR name = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,identifier);
            preparedStatement.setString(2,identifier);
            ResultSet resultSet = preparedStatement.executeQuery();
            while  (resultSet.next()) {
                contact = new Contact(resultSet.getString("number"));
                contact.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(contact);
    }

    @Override
    public int updateContactName(String number, String contactName) {
        int rowsAffected = 0;
        String sql = "UPDATE contact SET name = ? WHERE number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contactName);
            preparedStatement.setString(2, number);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }

    @Override
    public List<Contact> getAllContactsWithName() {
        String query = "SELECT * FROM contact WHERE name IS NOT NULL";
        Statement statement = null;
        List<Contact> contacts = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while  (resultSet.next()) {
                Contact contact = new Contact(resultSet.getString("number"));
                contact.setName(resultSet.getString("name"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }
}
