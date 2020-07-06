package com.miapp.tabmenuapp.DataBase;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract  ContactDao getContactDao();
    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config){
        return null;
    }
    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker(){return null;}

    @Override
    public void clearAllTables(){

    }
}
