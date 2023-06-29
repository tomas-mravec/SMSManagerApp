package com.example.smsmanagerapp.connection;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public interface ConnectionEstablisher {

    public Optional<Socket> getSocket();
    public PrintWriter getOutputStream();

    public void logIn();
}
