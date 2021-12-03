package me.varunon9.remotecontrolpc;

import android.content.Context;
import android.content.SharedPreferences;

public class SpHelp {
    SharedPreferences sp;

    public SpHelp(Context context){
        sp = context.getSharedPreferences("data", 0);
    }

    public void writeString(String key, String value){
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key){
        return sp.getString(key, "NULL");
    }
}
