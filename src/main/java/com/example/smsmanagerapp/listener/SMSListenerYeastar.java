package com.example.smsmanagerapp.listener;

import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.YeastarDeviceMessages;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SMSListenerYeastar implements MessageListener {

    private final Socket SOCKET;
    private PrintWriter out;
    private BufferedReader in;
    private List<SMSMessage> smsMessages;
   // private SMSMessage lastSMSMessage;
    private List<IMessageListenerObserver> listenerObservers;
    public SMSListenerYeastar(Socket socket) {
        this.smsMessages = new ArrayList<>();
        this.SOCKET = socket;
        this.listenerObservers = new ArrayList<>();
        setUpResources();
    }

    private void setUpResources() {
        try {
            out = new PrintWriter(SOCKET.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listenForMessages() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            String response;
            SMSMessage smsMessage = null;
            boolean buildingSMS = false;
            try {
                while ((response = in.readLine()) != null) {
                      System.out.println(response);

                      if (response.equals(YeastarDeviceMessages.getIncomingSMSNotificationString())) {
                          buildingSMS = true;
                          smsMessage = new SMSMessage();
                          System.out.println("building true");
                      } else if (response.equals(YeastarDeviceMessages.getEndSMSEvent())) {
                          buildingSMS = false;
                          smsMessages.add(smsMessage);
                          //lastSMSMessage = smsMessage;
                          notifyObservers(smsMessage);
                          System.out.println("Sender: " + smsMessage.getSender() + " Content: " + smsMessage.getContent());
                          smsMessage = null;
                          System.out.println("Koniec confirmed");
                      } else if (buildingSMS) {
                          smsMessage.updateData(response);
                          System.out.println("Updatujem data");
                      }

//                    messages.add(response);
//                    SmsMessage sms = SmsMessage.fromString(response);
//                    System.out.println("Sprava od " + sms.getSender() + " content " + sms.getContent());

                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    @Override
    public void addObserver(IMessageListenerObserver listenerObserver) {
        listenerObservers.add(listenerObserver);
    }

    @Override
    public void deleteObserver(IMessageListenerObserver listenerObserver) {
        listenerObservers.remove(listenerObserver);
    }

    @Override
    public void notifyObservers(Data data) {
        for (IMessageListenerObserver listenerObserver : listenerObservers) {
            listenerObserver.newMessage(data);
        }
    }
}
