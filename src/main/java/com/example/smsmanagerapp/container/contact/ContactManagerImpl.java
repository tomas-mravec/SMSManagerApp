package com.example.smsmanagerapp.container.contact;

import com.example.smsmanagerapp.connection.database.DatabaseConnection;
import com.example.smsmanagerapp.data.Contact;
import com.example.smsmanagerapp.data.SMSMessage;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManagerImpl implements ContactManager {

    private DatabaseConnection databaseConnection;
    private Connection connection;
    private Statement statement;


    public ContactManagerImpl(Connection connection) {
        this.connection = connection;
    }

//    public void setConnection(Connection connection) {
//        this.connection = connection;
//    }

    @Override
    public void createNewContact(Contact contact) {
        String sql = "INSERT INTO contact (number) VALUES (?)";
        try {
            //predtym by sa hodilo skontrolovat ci sa tel cislo uz v tabulke contact nachadza a ak hej tak pozret ci nema priradene meno a priradit ho aj kontaktu v sms sprave
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contact.getNumber());
            preparedStatement.executeUpdate();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateContactName(String number, String contactName) {
        String sql = "UPDATE contact SET name = ? WHERE number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contactName);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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