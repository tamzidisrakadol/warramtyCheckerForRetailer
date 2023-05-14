package com.example.warrantycheckerforretailer.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.example.warrantycheckerforretailer.models.StockModel;
import com.example.warrantycheckerforretailer.utlity.Constraints;
import com.example.warrantycheckerforretailer.utlity.KEYS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class StockActivity extends AppCompatActivity {

    ActivityStockBinding binding;
    ReportAdapter reportAdapter;
    ArrayList<StockModel> stockModel=new ArrayList<>();
    ArrayList<StockModel> sellModel=new ArrayList<>();
    ArrayList<StockModel> mainProduct=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        binding.stockRecyclerview.setHasFixedSize(true);
        binding.stockRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData();
        getSell();
        loadRecycler();
    }

    private void loadRecycler() {
        for (int i=0; i<stockModel.size();i++){
            if (sellModel.get(i).getBatterycode().equals(stockModel.get(i).getBatterycode())){
                mainProduct.add(stockModel.get(i));
            }

        }
    }

    private void getSell() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constraints.SELL_BATTERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0")){
                    try {
                        JSONArray jsonArray=new JSONArray(response);

                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            int id=jsonObject.getInt("id");
                            String vendorid=jsonObject.getString("vendorid");
                            String batterycode=jsonObject.getString("batterycode");
                            String scandate=jsonObject.getString("scandate");
                            String enddate=jsonObject.getString("enddate");
                            sellModel.add(new StockModel(id,vendorid,batterycode,scandate,enddate));

                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("id",Paper.book().read(KEYS.ID));
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getData() {
      StringRequest stringRequest=new StringRequest(Request.Method.POST, Constraints.BATTERY_INFO, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              if (!response.equals("0")){
                  try {
                      JSONArray jsonArray=new JSONArray(response);

                      for (int i =0;i<jsonArray.length();i++){
                          JSONObject jsonObject=jsonArray.getJSONObject(i);
                          int id=jsonObject.getInt("id");
                          String vendorid=jsonObject.getString("vendorid");
                          String batterycode=jsonObject.getString("batterycode");
                          String scandate=jsonObject.getString("scandate");
                          String enddate=jsonObject.getString("enddate");
                          stockModel.add(new StockModel(id,vendorid,batterycode,scandate,enddate));

                      }

                  } catch (JSONException e) {
                      throw new RuntimeException(e);
                  }

              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      }){
          @Nullable
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
              HashMap<String,String> hashMap=new HashMap<>();
              hashMap.put("id",Paper.book().read(KEYS.ID));
              return hashMap;
          }
      };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}