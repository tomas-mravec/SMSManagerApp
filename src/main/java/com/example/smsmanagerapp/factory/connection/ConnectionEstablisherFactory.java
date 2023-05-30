package com.example.smsmanagerapp.factory.connection;

import com.example.smsmanagerapp.connection.ConnectionEstablisher;
import com.example.smsmanagerapp.connection.ConnectionEstablisherYeastar;


public class ConnectionEstablisherFactory {

    public static ConnectionEstablisher create(String type, String ipAddress, int port, String username, String secret) {
        if (type.equals("Yeastar")) {
            return new ConnectionEstablisherYeastar(ipAddress, port, username, secret);
        }

        return null;
    }
}
