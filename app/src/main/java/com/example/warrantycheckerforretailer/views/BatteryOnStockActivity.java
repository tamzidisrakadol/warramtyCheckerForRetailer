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
import com.example.warrantycheckerforretailer.R;
import com.example.warrantycheckerforretailer.adapter.BatteryStockAdapter;
import com.example.warrantycheckerforretailer.adapter.StockAdapter;
import com.example.warrantycheckerforretailer.databinding.ActivityBatteryOnStockBinding;
import com.example.warrantycheckerforretailer.models.SellModle;
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

public class BatteryOnStockActivity extends AppCompatActivity {

    ActivityBatteryOnStockBinding binding;
    ArrayList<StockModel> stockModel = new ArrayList<>();
    BatteryStockAdapter stockAdapter;
    String TAG = "MyTag";
    ArrayList<StockModel> mainProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBatteryOnStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        this.setTitle("Stock");
        binding.stockRecyclerview.setHasFixedSize(true);
        binding.stockRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData();

    }
    private void loadRecycler(ArrayList<StockModel> stockModel0) {
        for (int i=0;i<stockModel0.size();i++){
            if (stockModel0.get(i).getVendorid().equals(Paper.book().read(KEYS.ID))){
                StockModel stock=stockModel0.get(i);
               // mainProduct.add(new StockModel(stock.getId(),stock.getVendorid(),stock.getBatterycode(),stock.getScandate(),stock.getEnddate()));
                //Toast.makeText(this, ""+stock.getVendorid(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "VENDOR ID_!"+stock.getVendorid());
                Log.d(TAG, "loadRecycler: "+mainProduct.size());
            }else{
                Toast.makeText(this, "not found !", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "Stock Size gg : "+stockModel0.size());
        }
//        if (mainProduct.size()!=0 || mainProduct.size()!=-1){
//            stockAdapter=new BatteryStockAdapter(getApplicationContext(),mainProduct);
//            binding.stockRecyclerview.setAdapter(stockAdapter);
//
//        }else{
//            Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
//        }

    }


    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.BATTERY_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            //Log.d(TAG, "Stock Count " + i);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String vendorid = jsonObject.getString("vendorid");
                            String batterycode = jsonObject.getString("batterycode");
                            String scandate = jsonObject.getString("scandate");
                            String enddate = jsonObject.getString("enddate");
                            stockModel.add(new StockModel(id, vendorid, batterycode, scandate, enddate));

                        }
                        loadRecycler(stockModel);

                        Log.d(TAG, "Stock Size on list: " + stockModel.size());

//            stockAdapter=new StockAdapter(getApplicationContext(),mainProduct);
//            binding.stockRecyclerview.setAdapter(stockAdapter);
//            stockAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Toast.makeText(BatteryOnStockActivity.this, "0 data !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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