package com.example.warrantycheckerforretailer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantycheckerforretailer.R;
import com.example.warrantycheckerforretailer.models.StockModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BatteryStockAdapter extends RecyclerView.Adapter<BatteryStockAdapter.viewholder>   {

    private Context context;
    private List<StockModel> stockModels;

    public BatteryStockAdapter(Context context, List<StockModel> stockModels) {
        this.context = context;
        this.stockModels = stockModels;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.stock_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        StockModel stockModel=stockModels.get(position);
        holder.code.setText("Battery Number : "+stockModel.getBatterycode());
        holder.scandate.setText("Scan Date : "+stockModel.getScandate());
        holder.enddate.setText("End Date : "+stockModel.getEnddate());
        holder.code.setText("Battery Number : "+stockModel.getBatterycode());
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formatCurrentDate = sdf.format(currentDate);


        try {
            Date firstDate = sdf.parse(formatCurrentDate);
            Date secondDate = sdf.parse(stockModel.getEnddate());

            Long difference = Math.abs(firstDate.getTime()-secondDate.getTime()) ;
            Long differenceToDay = difference/(24*60*60*1000);
            if (differenceToDay >= 0){
                holder.left.setText(String.valueOf(differenceToDay)+" days left");
            }else{
                holder.left.setText("0 days left");
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        return stockModels.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        TextView code,scandate,enddate,left;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.codeTV);
            scandate=itemView.findViewById(R.id.scanDateTV);
            enddate=itemView.findViewById(R.id.endDateTV);
            left=itemView.findViewById(R.id.dayleftTv);
        }
    }
}
