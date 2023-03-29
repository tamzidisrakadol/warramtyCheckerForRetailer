package com.example.warrantycheckerforretailer.views;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Bundle;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.adapter.CustomerListAdapter;
import com.example.warrantycheckerforretailer.databinding.ActivityCustomerListBinding;
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

public class CustomerListActivity extends AppCompatActivity {
    ActivityCustomerListBinding binding;
    private List<CustomerModel> customerModelList = new ArrayList<>();
    CustomerListAdapter customerListAdapter;
    private int retailerID = SharedPrefManager.getInstance(this).getRetailerID();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomerListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showCustomerList();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void showCustomerList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.get_Customer_List, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setCustomerName(jsonObject.getString("customerName"));
                    customerModel.setCustomerNumber(Long.parseLong(jsonObject.getString("customerNumber")));
                    customerModel.setBatteryBarcode(jsonObject.getString("batteryBarcode"));
                    customerModel.setSellingDate(jsonObject.getString("purchaseDate"));
                    customerModelList.add(customerModel);
                    customerListAdapter = new CustomerListAdapter(customerModelList);
                }
            } catch (JSONException e) {
                Log.d("errorTag",e.getMessage());
            }
            binding.recyclerView.setAdapter(customerListAdapter);
            customerListAdapter.notifyDataSetChanged();
        }, error -> {

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