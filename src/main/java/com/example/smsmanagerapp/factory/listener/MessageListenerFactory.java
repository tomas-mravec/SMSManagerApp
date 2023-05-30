package com.example.smsmanagerapp.factory.listener;

import com.example.smsmanagerapp.listener.MessageListener;
import com.example.smsmanagerapp.listener.SMSListenerYeastar;


import java.net.Socket;

public class MessageListenerFactory {
    public static MessageListener create(String type, Socket socket) {
        if (type.equals("SMS_Yeastar")) {
            return new SMSListenerYeastar(socket);
        }
        // Add other types of listeners here

        throw new IllegalArgumentException("Unsupported listener type: " + type);
    }
}
