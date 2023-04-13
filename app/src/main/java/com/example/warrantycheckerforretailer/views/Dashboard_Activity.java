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
import com.example.warrantycheckerforretailer.repository.SharedPrefManager;
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
    int i, x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        getCustomerList();
        getStock();

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

    @Override
    protected void onResume() {
        getCustomerList();
        super.onResume();
    }

    private void getStock() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.BATTERY_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                    }
                    loadSell();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
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

    private void loadSell() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.BATTERY_SELL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (x = 0; x < jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);

                    }
                    int stock = i - x;

                    binding.stock.setText(stock + " stock");
                    binding.dashboardSell.setText(x + " sell");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
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

    private void getCustomerList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.PROFILE, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String name = jsonObject.getString("Companyname");
                binding.dashboardRetailerNameTv.setText(name);

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