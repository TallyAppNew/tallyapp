package com.ngedev.tallyapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = DataNote.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoNote daoNote();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "db_note")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
