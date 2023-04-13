package com.example.warrantycheckerforretailer.views;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.databinding.ActivityDashboardBinding;
import com.example.warrantycheckerforretailer.models.CustomerModel;
import com.example.warrantycheckerforretailer.utlity.Constraints;
import com.example.warrantycheckerforretailer.utlity.KEYS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class Dashboard_Activity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    String TAG = "MyTag";
    List<CustomerModel> list = new ArrayList<>();
    String name;
    int i,x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        getSupportActionBar().hide();
        name=getIntent().getStringExtra("name");
        binding.dashboardRetailerNameTv.setText(name);
        getStock();
        //getSell();
       // Toast.makeText(this, ""+Paper.book().read(KEYS.ID), Toast.LENGTH_SHORT).show();

        binding.dashboardRetailerProfile.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard_Activity.this, ProfileActivity.class));
        });

        binding.dashboardCustomerBilling.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard_Activity.this, SellBatteryActivity.class));
        });
        binding.dashboardCustomerList.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard_Activity.this, CustomerListActivity.class));
        });


    }


    private void getStock() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.BATTERY_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if (!response.equals("0")){
                   try {
                       JSONArray jsonArray = new JSONArray(response);
                       for ( i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           Log.d(TAG, "onResponse: "+jsonObject.getString("id"));

                       }
                       getSell(i);
                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }
               }else{
                   getSell(0);
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard_Activity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", Paper.book().read(KEYS.ID));
                return hashMap;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void getSell(int i) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.BATTERY_SELL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (x = 0; x < jsonArray.length(); x++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            Log.d(TAG, "onResponse: "+jsonObject.getString("id"));

                        }
                        int stock=i-x;
                        binding.stock.setText(stock+" stock");
                        binding.dashboardSell.setText(x+" sell");

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    binding.dashboardSell.setText("0 sell");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard_Activity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", Paper.book().read(KEYS.ID));
                return hashMap;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}