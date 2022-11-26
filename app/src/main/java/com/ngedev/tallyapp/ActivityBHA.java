package com.ngedev.tallyapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngedev.core.HelperAds;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ActivityBHA extends AppCompatActivity {

    RecyclerView rv_data;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bha);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Capacity & Displacement");


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        rv_data = findViewById(R.id.rv_data);

        JSONArray arrayData = loadJson();

        RecyclerBHA adapter = new RecyclerBHA(arrayData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_data.setLayoutManager(layoutManager);
        rv_data.setAdapter(adapter);

//        HelperAds.showAds(getApplicationContext(), this, R.string.menu_ads);
    }

    JSONArray loadJson(){
        try{
            InputStream ism = getAssets().open("json/bha.json");
            int size = ism.available();
            byte[] buffer = new byte[size];
            ism.read(buffer);
            ism.close();
            String strJson = new String(buffer, "UTF-8");
            return new JSONArray(strJson);
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}