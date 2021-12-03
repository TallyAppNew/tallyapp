package com.ngedev.tallyapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
interface DaoNote {
    @Insert
    void insertNote(DataNote data);

    @Query("SELECT date FROM DataNote GROUP BY date")
    List<String> selectDate();

    @Query("SELECT note FROM DataNote WHERE date = :date")
    String selectNote(String date);

    @Query("UPDATE DataNote SET note = :note WHERE date = :date")
    void updateNote(String date, String note);

    @Query("DELETE FROM DataNote WHERE date = :date")
    void deleteNote(String date);
}
