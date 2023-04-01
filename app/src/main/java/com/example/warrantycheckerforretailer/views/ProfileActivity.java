package com.example.warrantycheckerforretailer.views;
import androidx.appcompat.app.AppCompatActivity;

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
        getSupportActionBar().hide();


        binding.profileComanyNameTV.setText(SharedPrefManager.getInstance(this).getCompanyName());
        binding.profileRetailerNameTV.setText(SharedPrefManager.getInstance(this).getRetailerName());
        binding.profileRetailerID.setText(String.valueOf(SharedPrefManager.getInstance(this).getRetailerID()));
        binding.profileRetailerPanNumber.setText(SharedPrefManager.getInstance(this).getRetailerPanNumber());


    }

}