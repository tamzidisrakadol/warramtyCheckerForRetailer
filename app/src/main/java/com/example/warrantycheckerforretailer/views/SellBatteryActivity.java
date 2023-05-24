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

import io.paperdb.Paper;

public class SellBatteryActivity extends AppCompatActivity {
    ActivitySellBatteryBinding binding;
//    private Boolean isDateSelected = false;
//    private Boolean isBarcodeScanned = false;
    private Boolean isBatteryValid = false;
    private String barcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Sell Battery");

        Paper.init(getApplicationContext());

        binding.sellBatteryBtn.setOnClickListener(v -> {

            String name,mail,phone,address;
            name=binding.customerNameET.getText().toString();
            mail=binding.customerMailET.getText().toString();
            phone=binding.customerPhoneET.getText().toString();
            address=binding.customerAddressET.getText().toString();
            barcodeValue = binding.barcodeTV.getText().toString();
            checkBattery(barcodeValue);
            if (barcodeValue.isEmpty()){
                Toast.makeText(this, "Please scan battery", Toast.LENGTH_SHORT).show();
                return;
           }if (name.isEmpty()){
                Toast.makeText(this, "Enter Name !", Toast.LENGTH_SHORT).show();
                return;
            }if (phone.isEmpty()){
                Toast.makeText(this, "Enter Phone !", Toast.LENGTH_SHORT).show();
                return;
            }if (address.isEmpty()){
                Toast.makeText(this, "Enter Address !", Toast.LENGTH_SHORT).show();
                return;
            }if (!isBatteryValid){
                Toast.makeText(this, "Battery Not Found !", Toast.LENGTH_SHORT).show();
                return;
            }
            register(name,mail,phone,address,barcodeValue);
            Log.d("error","value :"+barcodeValue);
        });

        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });

    }
    private void checkBattery(String code) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.CHECK_BATTERY, response -> {
            if (response.equals("found")){
                isBatteryValid = true;
            }
            else{
                binding.sellBatteryBtn.setEnabled(false);
                Toast.makeText(this, "Pleas scan valid battery", Toast.LENGTH_SHORT).show();
                isBatteryValid=false;
            }
        }, error ->
                Log.d("eTag", error.getMessage())) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("code", code);
                map.put("id", Paper.book().read(KEYS.ID));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
            checkBattery(barcodeValue);
        }
    });


    private void register(String name, String mail, String phone, String address,String code) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.SELL_BATTERY, response -> {
          if (response.equals("success")){
              Toast.makeText(this, "Successfully added !", Toast.LENGTH_SHORT).show();
              onBackPressed();
          }else{
              Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
              onBackPressed();
          }
        }, error ->
                Log.d("eTag", error.getMessage())) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("code", code);
                map.put("name", name);
                map.put("mail", mail);
                map.put("phone", phone);
                map.put("address", address);
                map.put("id", Paper.book().read(KEYS.ID));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}