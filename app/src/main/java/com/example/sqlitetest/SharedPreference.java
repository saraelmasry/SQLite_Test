package com.example.sqlitetest;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    SharedPreferences sharedPreferences;

    public SharedPreference(MainActivity context) {
        sharedPreferences=context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }

    public void SaveData(String UserName, String Password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("UserName",UserName);
        editor.putString("Password",Password);
        editor.apply();
    }

    public String LoadData(){
        String FileShareData="UserName:"+sharedPreferences.getString("UserName","no name");
        FileShareData+="Password:"+sharedPreferences.getString("Password","no pass");
        return FileShareData;
    }
}
