package com.example.warrantycheckerforretailer.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME= "mySharedPref12";
    public static final String KEY_companyName = "companyName";
    public static final String KEY_retailerName = "salesMan";
    public static final String KEY_USER_ID = "retailerId";
    public static final String KEY_retailer_PanNumber="panNumber";
    public static final String customer_list="customerList";


    private SharedPrefManager(Context context){
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance==null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userLogin(int id, String companyName, String salesMan,String panNumber){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_companyName,companyName);
        editor.putString(KEY_retailerName,salesMan);
        editor.putString(KEY_retailer_PanNumber,panNumber);
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

    public String getCompanyName(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_companyName,"");
    }


    public String getRetailerName(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_retailerName,"");
    }

    public int getRetailerID(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID,0);
    }

    public String getRetailerPanNumber(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_retailer_PanNumber,"");
    }
}
