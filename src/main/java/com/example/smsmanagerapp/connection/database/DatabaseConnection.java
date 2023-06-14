package com.example.smsmanagerapp.connection.database;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseConnection {

    public Connection getConnection();
    public Statement getStatement();
}
