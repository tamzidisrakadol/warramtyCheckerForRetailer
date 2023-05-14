package com.example.warrantycheckerforretailer.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.R;
import com.example.warrantycheckerforretailer.adapter.ReportAdapter;
import com.example.warrantycheckerforretailer.databinding.ActivityStockBinding;
import com.example.warrantycheckerforretailer.models.ReportModle;
import com.example.warrantycheckerforretailer.utlity.Constraints;
import com.example.warrantycheckerforretailer.utlity.KEYS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class StockActivity extends AppCompatActivity {

    ActivityStockBinding binding;
    ArrayList<ReportModle> reportModleArrayList = new ArrayList<>();
    ReportAdapter reportAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        binding.stockRecyclerview.setHasFixedSize(true);
        binding.stockRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData();
    }
    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.GET_MSG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String vendorID = jsonObject.getString("vendorid");
                            String code = jsonObject.getString("batterycode");
                            String date = jsonObject.getString("date");
                            String report = jsonObject.getString("smg");
                            String status = jsonObject.getString("status");
                           if (Paper.book().read(KEYS.ID).equals(vendorID)){
                               reportModleArrayList.add(new ReportModle(id, vendorID, code, date, report,status));
                           }
                        }
                        reportAdapter = new ReportAdapter(getApplicationContext(), reportModleArrayList);
                        binding.stockRecyclerview.setAdapter(reportAdapter);
                        binding.stockRecyclerview.scrollToPosition(reportModleArrayList.size() - 1);
                        reportAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(StockActivity.this, "Empty List !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}