package com.example.smsmanagerapp.connection.database;
import java.sql.*;

public class MySQLDatabaseConnection implements DatabaseConnection {


    private String url;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;

    public MySQLDatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        setUpConnection();
    }

    private void setUpConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("select * from message");
            System.out.println("Som za result setom");
            while(resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

}
