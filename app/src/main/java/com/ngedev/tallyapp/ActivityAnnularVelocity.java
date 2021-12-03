package com.ngedev.tallyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ActivityAnnularVelocity extends AppCompatActivity {

    TextInputEditText et_pump, et_annular;
    Button b_calculate;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annular_velocity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Annular velocity (ft/min)");

        et_pump = findViewById(R.id.et_pump);
        et_annular = findViewById(R.id.et_annular);
        b_calculate = findViewById(R.id.b_calculate);
        tv_result = findViewById(R.id.tv_result);

        b_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPump    = et_pump.getText().toString();
                String strAnnular = et_annular.getText().toString();

                if(strPump.length()==0){
                    et_pump.setError("Required");
                } else if(strAnnular.length()==0){
                    et_annular.setError("Required");
                } else {
                    float pump = Float.parseFloat(strPump);
                    float annular = Float.parseFloat(strAnnular);
                    float result = pump/annular;

                    tv_result.setText("AV = "+strPump+" bbl/min ÷ "+strAnnular+" bbl/ft \n= "+result+" ft/min");
                    tv_result.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}