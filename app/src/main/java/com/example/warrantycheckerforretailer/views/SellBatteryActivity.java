package com.example.warrantycheckerforretailer.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warrantycheckerforretailer.databinding.ActivitySellBatteryBinding;

public class SellBatteryActivity extends AppCompatActivity {
    ActivitySellBatteryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}