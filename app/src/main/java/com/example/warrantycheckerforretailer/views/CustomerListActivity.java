package com.example.warrantycheckerforretailer.views;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


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
import com.example.warrantycheckerforretailer.adapter.CustomerListAdapter;
import com.example.warrantycheckerforretailer.databinding.ActivityCustomerListBinding;
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

public class CustomerListActivity extends AppCompatActivity {
    ActivityCustomerListBinding binding;
    private List<CustomerModel> customerModelList = new ArrayList<>();
    CustomerListAdapter customerListAdapter;
    String TAG="MyTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomerListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        getSupportActionBar().setTitle("Sell battery");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        showCustomerList();

    }


    private void showCustomerList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.BATTERY_SELL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id=jsonObject.getInt("id");
                            String sellDate=jsonObject.getString("scandate");
                            String code=jsonObject.getString("batterycode");
                            String endDate=jsonObject.getString("enddate");
                            customerModelList.add(new CustomerModel(id,code,sellDate,endDate));

                        }
                        customerListAdapter=new CustomerListAdapter(customerModelList);
                        binding.recyclerView.setAdapter(customerListAdapter);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(CustomerListActivity.this, "Empty List !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerListActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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