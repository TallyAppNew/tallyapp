package com.ngedev.tallyapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.ngedev.core.HelperAds;

import java.util.ArrayList;
import java.util.Objects;

import me.varunon9.remotecontrolpc.MainActivity;

public class ActivityMenuMain extends AppCompatActivity {

    CardView cv_conversion, cv_assembly, cv_formula, cv_note, cv_monitor, cv_drilling_mud, cv_h2s, cv_drilling_rig_component;

    boolean isMenuOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        initAdsSDK();

        cv_conversion = findViewById(R.id.cv_basic);
        cv_assembly   = findViewById(R.id.cv_calculations);
        cv_formula    = findViewById(R.id.cv_drilling);
        cv_note       = findViewById(R.id.cv_engineer);
        cv_monitor    = findViewById(R.id.cv_pressure);
        cv_drilling_mud = findViewById(R.id.cv_drilling_mud);
        cv_h2s        = findViewById(R.id.cv_h2s);
        cv_drilling_rig_component = findViewById(R.id.cv_drilling_rig_component);


        cv_conversion.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ActivityConversion.class);
            startActivity(i);
            isMenuOpened = true;
        });

        cv_assembly.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ActivityBHA.class);
            startActivity(i);
            isMenuOpened = true;
        });

        cv_formula.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ActivityMenuDrillingFormula.class);
            startActivity(i);
            isMenuOpened = true;
        });

        cv_note.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ActivityDailyNote.class);
            startActivity(i);
            isMenuOpened = true;
        });

        cv_monitor.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            isMenuOpened = true;
        });

        cv_drilling_mud.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ActivityDrillingMud.class));
            isMenuOpened = true;
        });

        cv_h2s.setOnClickListener(view -> {
            gotoMenuGeneralActivity("h2s");
            isMenuOpened = true;
        });

        cv_drilling_rig_component.setOnClickListener(view -> {
            gotoMenuGeneralActivity("drilling_rig_components");
            isMenuOpened = true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_about){
            Intent i = new Intent(this, ActivityAbout.class);
            startActivity(i);
        } else if(item.getItemId()==R.id.menu_share){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download TallyApp on http://play.google.com/store/apps/details?id=com.ngedev.tallyapp");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        } else if(item.getItemId()==R.id.menu_rate){
            Uri uri = Uri.parse("market://details?id=com.ngedev.tallyapp");
            Intent goPlay = new Intent(Intent.ACTION_VIEW, uri);
            //flag untuk membuat onBack kembali ke apikasi
            goPlay.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goPlay);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.ngedev.tallyapp")));
            }
        } else if(item.getItemId()==R.id.menu_reference){
            Intent i = new Intent(this, ActivityReference.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if(isMenuOpened){
//            HelperAds.showAds(getApplicationContext(), ActivityMenuMain.this, R.string.return_ads);
//            isMenuOpened = false;
//        }
    }

    private void gotoMenuGeneralActivity(String action) {
        Intent i = new Intent(getApplicationContext(), ActivitySubMenu.class);
        i.setAction(action);
        startActivity(i);
    }

//    void initAdsSDK(){
//        MobileAds.initialize(this, initializationStatus ->
//               HelperAds.showAds(getApplicationContext(), ActivityMenuMain.this, R.string.menu_ads)
//        );

        //ArrayList<String> testDeviceIds = new ArrayList<>();
        //testDeviceIds.add("4126263DDEE370628E7428F7294C5842");

        //RequestConfiguration requestConfiguration = MobileAds.getRequestConfiguration()
         //       .toBuilder()
         //       .setTestDeviceIds(testDeviceIds)
         //       .build();
        //MobileAds.setRequestConfiguration(requestConfiguration);
//    }
}