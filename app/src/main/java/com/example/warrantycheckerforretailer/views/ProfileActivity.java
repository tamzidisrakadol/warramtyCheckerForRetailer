package com.example.warrantycheckerforretailer.views;

import static com.example.warrantycheckerforretailer.views.LogInActivity.retailerInfoCompanyName;
import static com.example.warrantycheckerforretailer.views.LogInActivity.retailerInfoID;
import static com.example.warrantycheckerforretailer.views.LogInActivity.retailerInfoName;
import static com.example.warrantycheckerforretailer.views.LogInActivity.retailerInfoPanNumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warrantycheckerforretailer.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profileComanyNameTV.setText(retailerInfoCompanyName);
        binding.profileRetailerNameTV.setText(retailerInfoName);
        binding.profileRetailerID.setText(String.valueOf(retailerInfoID));
        binding.profileRetailerPanNumber.setText(retailerInfoPanNumber);

    }

}