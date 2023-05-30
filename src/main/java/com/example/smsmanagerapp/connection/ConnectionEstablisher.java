package com.example.smsmanagerapp.connection;

import java.net.Socket;
import java.util.Optional;

public interface ConnectionEstablisher {

    public Optional<Socket> getSocket();
    public void logIn();
}
