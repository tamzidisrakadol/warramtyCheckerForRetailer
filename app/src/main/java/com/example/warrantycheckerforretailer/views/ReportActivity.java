package com.example.warrantycheckerforretailer.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.databinding.ActivityReportBinding;
import com.example.warrantycheckerforretailer.repository.CaptureAct;
import com.example.warrantycheckerforretailer.utlity.Constraints;
import com.example.warrantycheckerforretailer.utlity.KEYS;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class ReportActivity extends AppCompatActivity {
    ActivityReportBinding binding;
    Boolean isScanBarcode = false;
    String barcodeValue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        progressDialog = new ProgressDialog(ReportActivity.this);
        progressDialog.setTitle("Loading....");
        progressDialog.setCancelable(false);
        getSupportActionBar().setTitle("Report Battery");
        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });
        binding.submitBtn.setOnClickListener(v -> {
            String msg = binding.reportET.getText().toString();
            String code = binding.code.getText().toString();
            if (!isScanBarcode && code.isEmpty()) {
                Toast.makeText(this, "Please scan or put battery code fast !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.isEmpty()) {
                Toast.makeText(this, "Enter Message", Toast.LENGTH_SHORT).show();
                return;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.CHECK_BATTERY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("found")) {
                        if (barcodeValue==null){
                            sendReport(msg,code);
                            progressDialog.show();
                        }else {
                            sendReport(msg,barcodeValue);
                            progressDialog.show();
                        }
                    } else {
                        binding.submitBtn.setEnabled(false);
                        Toast.makeText(ReportActivity.this, "Scan Valid Battery", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ReportActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", Paper.book().read(KEYS.ID));
                    hashMap.put("code", code);
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        });


    }

    private void checkBattery(String code) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.CHECK_BATTERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("found")) {
                } else {
                    binding.submitBtn.setEnabled(false);
                    Toast.makeText(ReportActivity.this, "Scan Valid Battery", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReportActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", Paper.book().read(KEYS.ID));
                hashMap.put("code", code);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void sendReport(String msg,String code) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.REPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(ReportActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ReportActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", Paper.book().read(KEYS.ID));
                hashMap.put("msg", msg);
                hashMap.put("code", code);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
            binding.resultTV.setText(result.getContents());
            barcodeValue = result.getContents();
            isScanBarcode = true;
            checkBattery(barcodeValue);
        }
    });
}