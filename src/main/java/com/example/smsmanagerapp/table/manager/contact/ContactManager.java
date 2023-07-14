package com.example.smsmanagerapp.table.manager.contact;

import com.example.smsmanagerapp.data.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactManager {

    public boolean createNewContact(Contact contact);
    public Optional<Contact> findContact(String identifier);

    int updateContactName(String number, String contactName);

   List<Contact> getAllContactsWithName();

    List<Contact> filterContacts(String contactFilter);
}
