package com.ngedev.tallyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;


public class ActivityAddEditNote extends AppCompatActivity {

    String TAG  = "ActivityAddEditNote";
    AppDatabase mAppDatabase;
    String mDate;
    EditText et_note;
    boolean isNewNote = false;
    MenuItem menu_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        mDate = getIntent().getStringExtra("date");
        setTitle(LocalDate.parse(mDate).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

        mAppDatabase = AppDatabase.getAppDatabase(this);

        et_note = findViewById(R.id.et_note);

        String note = getIntent().getStringExtra("note");
        if(note==null){
            isNewNote = true;
        }
        et_note.setText(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        menu_delete = menu.findItem(R.id.menu_delete);
        if(isNewNote){
            menu_delete.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_save){
            saveNote();
            finish();
        } else if(item.getItemId()==R.id.menu_delete){
            mAppDatabase.daoNote().deleteNote(mDate);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveNote();
        super.onBackPressed();
    }

    void saveNote() {
        String note = et_note.getText().toString();
        if(note.length()>0){
            if(isNewNote){
                mAppDatabase.daoNote().insertNote(new DataNote(mDate, note));
            } else {
                mAppDatabase.daoNote().updateNote(mDate, note);
            }
        }
    }
}