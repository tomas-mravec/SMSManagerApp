package com.example.smsmanagerapp.sender;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MessageSenderYeastar implements MessageSender {

    private PrintWriter output;
    private int messageId;

    public MessageSenderYeastar(PrintWriter printWriter) {
        this.output = printWriter;
        messageId = 9877;
    }

    @Override
    public void sendMessage(String message, String number) {
        String port = "2";
//        try {
//            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
//            output.println("Action: smscommand");
//            output.println("command: gsm send sms " + port + " " + number + " " + encodedMessage + " " + messageId++);
//            output.println();
//            System.out.println("Poslal som spravu s textom: " + message + " na cislo: " + number);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }

    }
}
