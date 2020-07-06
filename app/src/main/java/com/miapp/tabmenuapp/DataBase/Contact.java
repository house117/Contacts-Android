package com.miapp.tabmenuapp.DataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "contact")
public class Contact {

    @PrimaryKey
    @NonNull
    private String mId;

    @ColumnInfo(name = "nombre")
    private String contactName;

    @ColumnInfo(name = "numero")
    private String contactNumber;

    @ColumnInfo(name = "favorito")
    private String contactoFavorito;



    public Contact() {
        mId = UUID.randomUUID().toString();
    }

    @NonNull
    public String getMId() {
        return mId;
    }

    public void setMId(@NonNull String _id) {
        mId = _id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String _contactName) {
        contactName = _contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String _contactNumber) {
        contactNumber = _contactNumber;
    }

    public String getContactoFavorito() {
        return contactoFavorito;
    }

    public void setContactoFavorito(String _contactoFavorito) {
        contactoFavorito = _contactoFavorito;
    }

}

