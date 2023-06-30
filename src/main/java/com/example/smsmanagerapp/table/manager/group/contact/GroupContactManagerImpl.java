package com.example.smsmanagerapp.table.manager.group.contact;


import com.example.smsmanagerapp.data.contact.Contact;

import java.sql.*;

public class GroupContactManagerImpl implements GroupContactManager {

    private int id;

    private Connection connection;

    public GroupContactManagerImpl(Connection connection) {
        this.connection = connection;
        this.id = getMaxIdFromDatabase() + 1;
        System.out.println("Najvyssie id group managera je: " + id);


        //Preco nezoberie najvyssie id z group contactu???


    }

    private int getMaxIdFromDatabase() {
        String sql = "SELECT MAX(id) FROM group_contact";
        int id = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                id = resultSet.getInt(1);
                System.out.println("Najvyssie id v group contact je: " + resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Value of id je: " + id);
        return id;
    }

    @Override
    public boolean addGroupContact(Contact contact, int id) {
        String sql = "INSERT INTO group_contact VALUES (?,?)";
        boolean newGroupContactCreated = false;
        try {
            //predtym by sa hodilo skontrolovat ci sa tel cislo uz v tabulke contact nachadza a ak hej tak pozret ci nema priradene meno a priradit ho aj kontaktu v sms sprave
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, contact.getNumber());
            preparedStatement.executeUpdate();
            newGroupContactCreated = true;
            System.out.println("Novy group contact bol vytvoreny s cislom " + contact.getNumber() + " a id: " + id);

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("group contact already saved");
            newGroupContactCreated = false;
        } catch (SQLException e) {
            newGroupContactCreated = true;
            throw new RuntimeException(e);
        }
        return newGroupContactCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
