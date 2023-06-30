package com.example.smsmanagerapp.table.manager.group.contact;

import com.example.smsmanagerapp.data.contact.Contact;

public interface GroupContactManager {
    public boolean addGroupContact(Contact contact, int id);
    public int getId();
    public void setId(int newId);
}
