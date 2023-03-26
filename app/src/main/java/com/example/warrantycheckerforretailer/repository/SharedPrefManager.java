package com.example.warrantycheckerforretailer.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME= "mySharedPref12";
    private static final String KEY_companyName = "companyName";
    private static final String KEY_retailerName = "salesMan";
    private static final String KEY_USER_ID = "retailerId";


    private SharedPrefManager(Context context){
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance==null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userLogin(int id, String companyName, String salesMan){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_companyName,companyName);
        editor.putString(KEY_retailerName,salesMan);
        editor.apply();
        return true;
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_companyName,null)!= null){
            return true;
        }
        return false;
    }


}
