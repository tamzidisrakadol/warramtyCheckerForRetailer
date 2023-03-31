package com.example.warrantycheckerforretailer.views;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.adapter.CustomerListAdapter;
import com.example.warrantycheckerforretailer.databinding.ActivityDashboardBinding;
import com.example.warrantycheckerforretailer.models.CustomerModel;
import com.example.warrantycheckerforretailer.repository.SharedPrefManager;
import com.example.warrantycheckerforretailer.utlity.Constraints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard_Activity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private int retailerID = SharedPrefManager.getInstance(this).getRetailerID();
    List<CustomerModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(Dashboard_Activity.this,LogInActivity.class);
            startActivity(intent);
            return;
        }

        binding.dashboardRetailerProfile.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard_Activity.this,ProfileActivity.class));
        });

        binding.dashboardCustomerBilling.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard_Activity.this, SellBatteryActivity.class));
        });
        binding.dashboardCustomerList.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard_Activity.this,CustomerListActivity.class));
        });

        binding.dashboardRetailerNameTv.setText(SharedPrefManager.getInstance(this).getRetailerName());
        getCustomerList();
    }

    private void getCustomerList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.get_Customer_List, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setCustomerName(jsonObject.getString("customerName"));
                    list.add(customerModel);
                }
            } catch (JSONException e) {
                Log.d("errorTag",e.getMessage());
            }
            binding.dashboardTotalBatteryTV.setText(String.valueOf(list.size())+" pc");

        }, error -> {
            Log.d("tag",error.getMessage());
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("retailerID",String.valueOf(retailerID));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}