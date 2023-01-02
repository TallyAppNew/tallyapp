package com.ngedev.tallyapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ngedev.core.HelperAds;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ActivityDailyNote extends AppCompatActivity {
    String TAG = "ActivityDailyNote";

    MaterialCalendarView mcv_calendar;
    private ArrayList<Date> markedDates = new ArrayList<>();
    FloatingActionButton fab_addEdit;
    TextView tv_preview;
//    private AdView mAdView;

    String mSelectedDate;

    AppDatabase mAppDatabase;
    boolean isNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_note);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Daily Note");

        mAppDatabase = AppDatabase.getAppDatabase(this);

        mcv_calendar = findViewById(R.id.mcv_calendar);
        fab_addEdit  = findViewById(R.id.fab_addEdit);
        tv_preview   = findViewById(R.id.tv_preview);


//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });

//        mAdView = findViewById(R.id.adView3);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        mcv_calendar.setOnDateChangedListener((widget, date, selected) -> {
            mSelectedDate = date.getDate().toString();
            dateSelected(mSelectedDate);
        });


        fab_addEdit.setOnClickListener((View view) -> {
            Intent i = new Intent(this, ActivityAddEditNote.class);
            i.putExtra("date", mSelectedDate);
            if(!isNewNote){
                i.putExtra("note", tv_preview.getText().toString());
            }

            startActivity(i);
        });

//        HelperAds.showAds(getApplicationContext(), this, R.string.menu_ads);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> listDate = mAppDatabase.daoNote().selectDate();

        markedDates.clear();
        for(String date : listDate){
            String[] dateNote  = date.split("-");
            int year  = Integer.parseInt(dateNote[0]);
            int month = Integer.parseInt(dateNote[1]);
            int day   = Integer.parseInt(dateNote[2]);
            markedDates.add(new GregorianCalendar(year, month-1, day ).getTime());
        }

        List<CalendarDay> list = new ArrayList<>();
        for (Date date : markedDates) {
            LocalDate dateThreeTenFormat = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            CalendarDay calendarDay = CalendarDay.from(dateThreeTenFormat);
            list.add(calendarDay);
        }

        mcv_calendar.setSelectionColor(Color.parseColor("#607d8b"));
        mcv_calendar.setSelectedDate(LocalDate.now());
        mSelectedDate = LocalDate.now().toString();
        dateSelected(mSelectedDate);
        mcv_calendar.removeDecorators();
        mcv_calendar.addDecorators(new EventDecorator(list));
    }

    void dateSelected(String date){
        String note = mAppDatabase.daoNote().selectNote(date);
        if(note!=null){
            tv_preview.setText(note);
            if (isTablet()) tv_preview.setTextSize(24f);
            isNewNote = false;
            fab_addEdit.setImageResource(R.drawable.i_edit);
        } else {
            tv_preview.setText("Note is empty.");
            if (isTablet()) tv_preview.setTextSize(24f);
            isNewNote = true;
            fab_addEdit.setImageResource(R.drawable.i_add);
        }
    }

    Boolean isTablet(){
        return getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    private class EventDecorator implements DayViewDecorator {

        private final HashSet<CalendarDay> dates;

        public EventDecorator(Collection<CalendarDay> dates) {
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.background_event, null));
            //view.addSpan(new DotSpan(5, color));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}