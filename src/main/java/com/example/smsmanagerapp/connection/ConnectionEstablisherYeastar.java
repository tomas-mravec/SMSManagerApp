package com.example.smsmanagerapp.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class ConnectionEstablisherYeastar implements ConnectionEstablisher {

    private final String IP_ADDRESS;
    private final int PORT;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final String USERNAME;
    private final String SECRET;



    public ConnectionEstablisherYeastar(String ipAddress, int port, String username, String secret) {
        this.IP_ADDRESS = ipAddress;
        this.PORT = port;
        this.USERNAME = username;
        this.SECRET = secret;
        setUpConnection();
    }

    public void setUpConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
//            socket = new Socket(IP_ADDRESS, PORT);
//            out = new PrintWriter(socket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            } catch (IOException e) {
                System.out.println("Connection failed");
                socket = null;
                out = null;
                in = null;
        }
    }

    public void logIn() {
        if (socket != null && in != null && out != null) {
            out.println("Action: Login");
            out.println("Username: " + USERNAME);
            out.println("Secret: " + SECRET);
            out.println();

            //Reading response from device
            String response;
            for (int i = 0; i < 3; i++) {
                try {
                    response = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(response);
            }
            //sendMessage();

            //closeResources();
        }
    }

    private void sendMessage() {
//        out.println("Action: smscommand");
//        String command = "";
//
//
//        out.println("Command: " + command);

    }

    private void closeResources() {
//        try {
//            //in.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//       // out.close();
    }

    @Override
    public PrintWriter getOutputStream() {
        return out;
    }

    public BufferedReader getInputStream() {
        return in;
    }
    @Override
    public Optional<Socket> getSocket() {
        return Optional.ofNullable(socket);
    }
}
