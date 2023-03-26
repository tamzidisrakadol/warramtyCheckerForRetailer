package com.example.warrantycheckerforretailer.views;

import static com.example.warrantycheckerforretailer.views.LogInActivity.panNumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.warrantycheckerforretailer.R;
import com.example.warrantycheckerforretailer.databinding.ActivityDashboardBinding;
import com.example.warrantycheckerforretailer.repository.SharedPrefManager;

public class Dashboard_Activity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(Dashboard_Activity.this,LogInActivity.class);
            startActivity(intent);
            return;
        }

       binding.textView.setOnClickListener(v -> {
           startActivity(new Intent(Dashboard_Activity.this,ProfileActivity.class));
       });



    }
}