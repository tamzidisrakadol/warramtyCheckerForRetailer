package com.example.warrantycheckerforretailer.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.databinding.ActivityLoginBinding;
import com.example.warrantycheckerforretailer.repository.SharedPrefManager;
import com.example.warrantycheckerforretailer.utlity.Constraints;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(this,Dashboard_Activity.class);
            startActivity(intent);
            return;
        }

        binding.loginBtn.setOnClickListener(v -> {
            if (isDataValid()) {
                login();
            }
        });
    }


    //checking validity
    private boolean isDataValid() {
        if (binding.loginCompanyNameET.getText().toString().isEmpty()) {
            binding.loginCompanyNameET.requestFocus();
            return false;
        } else if (binding.retailerNameEt.getText().toString().isEmpty()) {
            binding.retailerNameEt.requestFocus();
            return false;
        }
        return true;

    }

    //login to Dashboard
    private void login() {
        String companyName = binding.loginCompanyNameET.getText().toString().trim();
        String retailerName = binding.retailerNameEt.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.Url_login, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.getBoolean("error")) {
                    SharedPrefManager.getInstance(getApplicationContext())
                            .userLogin(jsonObject.getInt("id")
                                    , jsonObject.getString("companyName")
                                    , jsonObject.getString("salesMan")
                                    , jsonObject.getString("panNumber"));
                    Toast.makeText(LogInActivity.this, "User login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this, Dashboard_Activity.class));
                    finish();
                } else {
                    Toast.makeText(LogInActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.i("exception", e.toString());
            }
        }, error -> {
            Log.d("ETag", "error" + error.toString());
            Toast.makeText(LogInActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("companyName", companyName);
                map.put("salesMan", retailerName);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}