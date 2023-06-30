package com.example.smsmanagerapp.data.contact;

import com.example.smsmanagerapp.data.Data;

import java.time.LocalDateTime;

public class SMSMessageOut implements Data {
    private int id;
    private String content;
    private GroupContact groupContact;
    private LocalDateTime time;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GroupContact getGroupContact() {
        return groupContact;
    }

    public void setGroupContact(GroupContact groupContact) {
        this.groupContact = groupContact;
    }
}
