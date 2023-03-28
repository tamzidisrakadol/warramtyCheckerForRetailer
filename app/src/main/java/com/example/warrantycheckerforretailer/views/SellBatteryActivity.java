package com.example.warrantycheckerforretailer.views;

import static com.example.warrantycheckerforretailer.views.ProfileActivity.rID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.databinding.ActivitySellBatteryBinding;
import com.example.warrantycheckerforretailer.repository.CaptureAct;
import com.example.warrantycheckerforretailer.utlity.Constraints;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SellBatteryActivity extends AppCompatActivity {
    ActivitySellBatteryBinding binding;
    Boolean isDateSelected = false;
    Boolean isBarcodeScanned = false;
    String selectedDate;
    String barcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sellBatteryBtn.setOnClickListener(v -> {
            if (isDataValid() && isDateSelected && isBarcodeScanned){
                register();
            }
        });

        binding.sellingDateTV.setOnClickListener(v -> {
            pickUpDate(v);
        });

        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });

    }

    //checking data validity
    private boolean isDataValid(){
        if (binding.sellBatteryCustomerNameET.getText().toString().isEmpty()){
            binding.sellBatteryCustomerNameET.requestFocus();
            return false;
        } else if (binding.sellBatteryCustomerNumberET.getText().toString().isEmpty()) {
            binding.sellBatteryCustomerNumberET.requestFocus();
            return false;
        }
        return true;
    }


    private void pickUpDate(View view){
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = String.valueOf(year+"/"+(month+1)+"/"+dayOfMonth);
                binding.sellingDateTV.setText(selectedDate);
                isDateSelected = true;
            }
        }, year, month, day);
        dpd.show();
    }

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
            isBarcodeScanned = true;
        }
    });


    private void register(){
        String customerName = binding.sellBatteryCustomerNameET.getText().toString();
        String customerNumber = binding.sellBatteryCustomerNumberET.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.add_customer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")){
                        Toast.makeText(SellBatteryActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(SellBatteryActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("eTag",error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("customerName",customerName);
                map.put("customerNumber",customerNumber);
                map.put("batteryBarcode",barcodeValue);
                map.put("retailerID",String.valueOf(rID));
                map.put("purchaseDate",selectedDate);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}