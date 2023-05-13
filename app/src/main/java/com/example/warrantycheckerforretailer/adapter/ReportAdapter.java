package com.example.warrantycheckerforretailer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantycheckerforretailer.R;
import com.example.warrantycheckerforretailer.models.ReportModle;
import com.example.warrantycheckerforretailer.utlity.Constraints;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.viewholder> {

    private Context context;
    private List<ReportModle> reportModles;

    public ReportAdapter(Context context, List<ReportModle> reportModles) {
        this.context = context;
        this.reportModles = reportModles;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ReportModle reportModle = reportModles.get(position);


        if (reportModle.getStatus().equals("1")){
            holder.status.setText("Pending !");
        }else{
            holder.status.setText("Solved !");
        }

        holder.code.setText(reportModle.getBatterycode());
        holder.date.setText(reportModle.getDate());
    }

    @Override
    public int getItemCount() {
        return reportModles.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView code, date,status;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.productCode);
            date = itemView.findViewById(R.id.productDate);
            status = itemView.findViewById(R.id.statusTV);
        }
    }
}
