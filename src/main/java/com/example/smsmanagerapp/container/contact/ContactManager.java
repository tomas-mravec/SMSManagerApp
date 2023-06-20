package com.example.smsmanagerapp.container.contact;

import com.example.smsmanagerapp.data.Contact;

import java.util.List;

public interface ContactManager {

    public void createNewContact(Contact contact);

    void updateContactName(String number, String contactName);

   List<Contact> getAllContactsWithName();
}
