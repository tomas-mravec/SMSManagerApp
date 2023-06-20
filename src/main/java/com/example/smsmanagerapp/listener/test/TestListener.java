package com.example.smsmanagerapp.listener.test;

import com.example.smsmanagerapp.data.Contact;
import com.example.smsmanagerapp.data.Data;
import com.example.smsmanagerapp.data.SMSMessage;
import com.example.smsmanagerapp.gui.GUINotifier;
import com.example.smsmanagerapp.interfaces.IMessageListenerObserver;
import com.example.smsmanagerapp.listener.MessageListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestListener implements MessageListener {

    private List<IMessageListenerObserver> listenerObservers;

    public TestListener() {
        this.listenerObservers = new ArrayList<>();
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
        System.out.println("ALOHA");
        for (IMessageListenerObserver listenerObserver : listenerObservers) {
            System.out.println("MATA");
            listenerObserver.newMessage(data);
            System.out.println("KATATA");
        }
    }

    @Override
    public void listenForMessages() {
//            System.out.println("Som v listen for messages testlistener");
//            SMSMessage smsMessage;
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i);
//                smsMessage = new SMSMessage();
//                //smsMessage.setSender("+041 " + i);
//                smsMessage.setContact(new Contact("+031 " + i));
//                smsMessage.setRecvTime(LocalDateTime.now());
//                if (i % 2 == 0) {
//                    smsMessage.setContent("Sprava od mna cislo " + i + "BLABLABLABLABLABLABLABLABLABLABLABLABALBAL");
//                } else {
////                    smsMessage.setContent("Raz DVA TRI Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus tincidunt lorem lorem, eu vehicula eros dictum ut. Donec in finibus mi. Aenean vulputate purus eu leo hendrerit dapibus. Vestibulum lacinia metus erat, id tempus orci fringilla posuere. Ut euismod urna sit amet libero eleifend vestibulum. Praesent non odio a felis tempus elementum nec vel ex. Fusce iaculis congue urna et placerat. Nulla vitae volutpat velit, id vulputate metus. Nullam sollicitudin nibh luctus metus finibus condimentum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce sodales augue at nisi gravida dignissim. Aliquam lobortis mauris et molestie laoreet. Etiam at imperdiet nunc.\n" +
////                            "\n" +
////                            "Nulla nisi erat, porttitor vitae sollicitudin ut, imperdiet eu risus. Phasellus vehicula elementum ligula sit amet eleifend. Cras sem sapien, ullamcorper id blandit at, tempor nec mi. Aliquam venenatis ante urna. Nam a nisl nisi. Suspendisse in mattis nulla, in ultrices nisi. Etiam et velit ante. " + i);
////                }
//                    smsMessage.setContent("Sprava od mna cislo " + i + "AEAEAEAEAEAEAEAEAEAE");
//
//                }
//                System.out.println("KEK");
//                //notifyObservers(smsMessage);
//                System.out.println("KEK2");
//            }
    }
}
