package com.ngedev.tallyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ActivityMenuDrillingFormula extends AppCompatActivity {

    CardView cv_basic, cv_calculations, cv_drilling, cv_engineer, cv_pressure;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drilling_formula);
        setTitle("Drilling Formula");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        cv_basic        = findViewById(R.id.cv_basic);
        cv_calculations = findViewById(R.id.cv_calculations);
        cv_drilling     = findViewById(R.id.cv_drilling);
        cv_engineer     = findViewById(R.id.cv_engineer);
        cv_pressure     = findViewById(R.id.cv_pressure);

        cv_basic.setOnClickListener(view -> {
            gotoMenuFormulaActivity("basic_formula");

        });
        cv_calculations.setOnClickListener(view -> {
            gotoMenuFormulaActivity("calculations");
            //HelperAds.showAds(getApplicationContext(), ActivityMenuDrillingFormula.this, R.string.menu_ads);
        });
        cv_drilling.setOnClickListener(view -> {
            gotoMenuFormulaActivity("drilling");
            //HelperAds.showAds(getApplicationContext(), ActivityMenuDrillingFormula.this, R.string.menu_ads);
        });
        cv_engineer.setOnClickListener(view -> {
            gotoMenuFormulaActivity("engineering");
            //HelperAds.showAds(getApplicationContext(), ActivityMenuDrillingFormula.this, R.string.test_ads);
        });
        cv_pressure.setOnClickListener(view -> {
            gotoMenuFormulaActivity("pressure");
            //HelperAds.showAds(getApplicationContext(), ActivityMenuDrillingFormula.this, R.string.menu_ads);
        });
    }

    private void gotoMenuFormulaActivity(String action) {
        Intent i = new Intent(getApplicationContext(), ActivitySubMenu.class);
        i.setAction(action);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}