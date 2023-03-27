package com.example.warrantycheckerforretailer.views;

import static com.example.warrantycheckerforretailer.repository.SharedPrefManager.KEY_USER_ID;
import static com.example.warrantycheckerforretailer.repository.SharedPrefManager.KEY_companyName;
import static com.example.warrantycheckerforretailer.repository.SharedPrefManager.KEY_retailerName;
import static com.example.warrantycheckerforretailer.repository.SharedPrefManager.KEY_retailer_PanNumber;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.warrantycheckerforretailer.databinding.ActivityProfileBinding;
import com.example.warrantycheckerforretailer.repository.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPref12", Context.MODE_PRIVATE);
        String cName = sharedPreferences.getString(KEY_companyName,"");
        String rName= sharedPreferences.getString(KEY_retailerName,"");
        int rID = sharedPreferences.getInt(KEY_USER_ID,0);
        String pNumber=sharedPreferences.getString(KEY_retailer_PanNumber,"");




        binding.profileComanyNameTV.setText(cName);
        binding.profileRetailerNameTV.setText(rName);
        binding.profileRetailerID.setText(String.valueOf(rID));
        binding.profileRetailerPanNumber.setText(pNumber);

    }

}