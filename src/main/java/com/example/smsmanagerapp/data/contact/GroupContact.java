package com.example.smsmanagerapp.data.contact;

import java.util.ArrayList;
import java.util.List;

public class GroupContact {

    private List<Contact> contacts;

    public GroupContact() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public Iterable<? extends Contact> getContacts() {
        return contacts;
    }
}
