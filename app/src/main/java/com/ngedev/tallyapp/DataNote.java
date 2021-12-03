package com.ngedev.tallyapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataNote {
    @PrimaryKey @NonNull String date;
    String note;

    public DataNote(String date, String note){
        this.date = date;
        this.note = note;
    }
}
