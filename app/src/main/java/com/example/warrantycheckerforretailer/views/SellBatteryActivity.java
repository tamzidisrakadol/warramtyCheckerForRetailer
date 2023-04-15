package com.example.warrantycheckerforretailer.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.databinding.ActivitySellBatteryBinding;
import com.example.warrantycheckerforretailer.repository.CaptureAct;
import com.example.warrantycheckerforretailer.utlity.Constraints;
import com.example.warrantycheckerforretailer.utlity.KEYS;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import io.paperdb.Paper;

public class SellBatteryActivity extends AppCompatActivity {
    ActivitySellBatteryBinding binding;
    private Boolean isDateSelected = false;
    private Boolean isBarcodeScanned = false;
    private String selectedDate, expireDate;
    private String barcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Sell Battery");
        Paper.init(getApplicationContext());
        binding.sellBatteryBtn.setOnClickListener(v -> {
            if (barcodeValue==null){
                Toast.makeText(this, "Please scan battery", Toast.LENGTH_SHORT).show();
               return;
           }
            register();
        });
        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });

    }
    private void checkBattery() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.CHECK_BATTERY, response -> {
            if (response.equals("found")){
            }else{
                binding.sellBatteryBtn.setEnabled(false);
                Toast.makeText(this, "Pleas scan valid battery", Toast.LENGTH_SHORT).show();
            }
        }, error ->
                Log.d("eTag", error.getMessage())) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("code", barcodeValue);
                map.put("id", Paper.book().read(KEYS.ID));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void batteryExpireDate(int year, int month, int dayOfMonth) {
        expireDate = String.valueOf(year + "-" + (month + 3) + "-" + dayOfMonth);
    }

    //scan barcode of battery
    private void scanBarcode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Volume up to flash on");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            binding.barcodeTV.setText(result.getContents());
            barcodeValue = result.getContents();
            checkBattery();
            isBarcodeScanned = true;
        }
    });


    private void register() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.SELL_BATTERY, response -> {
          if (response.equals("success")){
              Toast.makeText(this, "Successfully added !", Toast.LENGTH_SHORT).show();
              onBackPressed();
          }else{
              Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
          }
        }, error ->
                Log.d("eTag", error.getMessage())) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("code", barcodeValue);
                map.put("id", Paper.book().read(KEYS.ID));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}