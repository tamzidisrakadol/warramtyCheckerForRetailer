package com.example.warrantycheckerforretailer.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.warrantycheckerforretailer.utlity.KEYS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class LogInActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        checkLogin();
        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(LogInActivity.this);
        progressDialog.setTitle("Lading...");
        progressDialog.setCancelable(false);

        binding.loginBtn.setOnClickListener(v -> {
            if (isDataValid()) {
                login();
            }
        });
    }

    private void checkLogin() {
        if (Paper.book().read(KEYS.ID)!=null){
            binding.view.setVisibility(View.INVISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.PROFILE, response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String name = jsonObject.getString("Companyname");
                        startActivity(new Intent(getApplicationContext(),Dashboard_Activity.class)
                                .putExtra("name",name)
                        );
                        finishAffinity();

                    } catch (JSONException e) {
                        Log.d("errorTag", e.getMessage());
                    }

                }, error -> {
                    Log.d("tag", error.getMessage());
                    Toast.makeText(this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", Paper.book().read(KEYS.ID));
                        return map;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }



    }


    //checking data validity
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
        progressDialog.show();
        String companyName = binding.loginCompanyNameET.getText().toString().trim();
        String retailerName = binding.retailerNameEt.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.LOGIN, response -> {

            progressDialog.dismiss();
            if (!response.toString().equals("user not found")){
                Toast.makeText(this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                Paper.book().write(KEYS.ID,response);
                startActivity(new Intent(getApplicationContext(),Dashboard_Activity.class));
                finishAffinity();

            }else{
                Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Log.d("ETag", "error" + error.toString());
            Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", companyName);
                map.put("password", retailerName);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}