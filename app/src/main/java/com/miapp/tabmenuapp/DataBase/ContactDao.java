package com.miapp.tabmenuapp.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getContacts();

    @Query("SELECT * FROM contact WHERE mId LIKE :uuid")
    Contact getContact(String uuid);

   // @Query("SELECT * FROM contact WHERE title LIKE :title")
    //List<Contact>  getContactTitle(String title);

    @Insert
    void addContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Update
    void updateContact(Contact contact);
}
