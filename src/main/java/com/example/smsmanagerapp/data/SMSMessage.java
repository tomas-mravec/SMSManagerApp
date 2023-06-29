package com.example.smsmanagerapp.data;

import com.example.smsmanagerapp.data.contact.Contact;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SMSMessage implements Data {
    private String event;
    private String privilege;
    private int id;
    private int gsmSpan;
    private String sender;
    private LocalDateTime recvTime;
    private int index;
    private int total;
    private String smsc;
    private String content;
    private boolean seen = false;
    private int contactId;
    private Contact contact;


// constructor, getters, and setters omitted for brevity

    public static SMSMessage fromString(String smsString) {
        SMSMessage sms = new SMSMessage();
        String[] lines = smsString.split("\n");
        for (String line : lines) {
            String[] parts = line.split(": ");
            String key = parts[0].trim();
            String value = parts[1].trim();
            switch (key) {
                case "Event":
                    sms.setEvent(value);
                    break;
                case "Privilege":
                    sms.setPrivilege(value);
                    break;
                case "ID":
                    //sms.setId(value);
                    break;
                case "GsmSpan":
                    sms.setGsmSpan(Integer.parseInt(value));
                    break;
                case "Sender":
                    sms.setSender(value);
                    break;
                case "Recvtime":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    sms.setRecvTime(LocalDateTime.parse(value, formatter));
                    break;
                case "Index":
                    sms.setIndex(Integer.parseInt(value));
                    break;
                case "Total":
                    sms.setTotal(Integer.parseInt(value));
                    break;
                case "Smsc":
                    sms.setSmsc(value);
                    break;
                case "Content":
                    sms.setContent(value);
                    break;
                default:
                    // ignore unknown keys
                    break;
            }
        }
        return sms;
    }

    public void updateData(String response) {
        String[] parts = response.split(": ");
        if (parts.length > 1) {
            String key = parts[0];
            String value = parts[1];
            System.out.println(" Som vnutry updateData key je " + key + " Value je " + value);
            switch (key) {
                case "Content":
                    setContent(value);
                    break;
                case "Sender":
                    setContact(new Contact(value));
                    break;
                case "Recvtime":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    setRecvTime(LocalDateTime.parse(value, formatter));
                    break;
            }
        }
    }

    private void setPrivilege(String value) {
    }

    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setGsmSpan(int parseInt) {

    }
    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private void setIndex(int parseInt) {

    }

    private void setTotal(int parseInt) {

    }

    public void setContent(String content) {
        this.content = content;
    }

    private void setSmsc(String value) {

    }

    public void setRecvTime(LocalDateTime recvTime) {
        this.recvTime = recvTime;
    }

    private void setEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public String getPrivilege() {
        return privilege;
    }

    public int getId() {
        return id;
    }

    public int getGsmSpan() {
        return gsmSpan;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getRecvTime() {
        return recvTime;
    }

    public int getIndex() {
        return index;
    }

    public int getTotal() {
        return total;
    }

    public String getSmsc() {
        return smsc;
    }

    public String getContent() {
        return content;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

