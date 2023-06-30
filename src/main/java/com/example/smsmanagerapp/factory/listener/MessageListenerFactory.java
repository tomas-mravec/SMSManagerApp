package com.example.smsmanagerapp.factory.listener;

import com.example.smsmanagerapp.listener.MessageListener;
import com.example.smsmanagerapp.listener.SMSListenerYeastar;
import com.example.smsmanagerapp.response.ResponseContainerSMSYeastar;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageListenerFactory {
    public static MessageListener create(String type, Socket socket, PrintWriter out, BufferedReader in) {
        if (type.equals("SMS_Yeastar")) {
            return new SMSListenerYeastar(socket, out, in, new ResponseContainerSMSYeastar());
        }
        // Add other types of listeners here

        throw new IllegalArgumentException("Unsupported listener type: " + type);
    }
}
