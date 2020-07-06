package com.miapp.tabmenuapp;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.miapp.tabmenuapp.DataBase.Contact;
import com.miapp.tabmenuapp.DataBase.ContactDao;
import com.miapp.tabmenuapp.DataBase.ContactDatabase;

import java.util.ArrayList;

public class ContactDaoImplement {
    @SuppressLint("StaticFieldLeak")
    private static ContactDaoImplement contactDaoImplement;
    private ContactDao contactDao;

    private ContactDaoImplement(Context context){
        Context appContext = context.getApplicationContext();
        ContactDatabase database = Room.databaseBuilder(appContext, ContactDatabase.class, "Contact")
                .allowMainThreadQueries().build();
        contactDao = database.getContactDao();

    }

    public static ContactDaoImplement get(Context context){
        if(contactDaoImplement == null){
            contactDaoImplement = new ContactDaoImplement(context);

        }
        return contactDaoImplement;
    }

    public ArrayList<Contact> getContacts(){ return (ArrayList<Contact>) contactDao.getContacts();}

    public Contact getContact(String id){ return contactDao.getContact(id);}

    public void addContact(Contact contact){  contactDao.addContact(contact);}

    public void  updateContact (Contact contact){contactDao.updateContact(contact);}

    public void deleteContact(Contact contact){contactDao.deleteContact(contact);}

}
