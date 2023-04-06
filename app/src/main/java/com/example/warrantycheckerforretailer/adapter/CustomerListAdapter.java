package com.example.warrantycheckerforretailer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantycheckerforretailer.databinding.CustomerListItemBinding;
import com.example.warrantycheckerforretailer.models.CustomerModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder>{
    List<CustomerModel> customerModelList;
    public CustomerListAdapter(List<CustomerModel> customerModelList) {
        this.customerModelList = customerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CustomerListItemBinding customerListItemBinding = CustomerListItemBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(customerListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerModel customerModel = customerModelList.get(position);
        Date currentDate = Calendar.getInstance().getTime();
        String expireDate = customerModel.getExpireDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatCurrentDate = sdf.format(currentDate);

        try {
            Date firstDate = sdf.parse(formatCurrentDate);
            Date secondDate = sdf.parse(expireDate);
            Long difference = Math.abs(firstDate.getTime()-secondDate.getTime()) ;
            Long differenceToDay = difference/(24*60*60*1000);
            if (differenceToDay >= 0){
                holder.customerListItemBinding.expireDate.setText(differenceToDay+" days left");
            }else{
                holder.customerListItemBinding.expireDate.setText("0");
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        holder.customerListItemBinding.listCustomerName.setText(customerModel.getCustomerName());
        holder.customerListItemBinding.listCustomerNumber.setText(customerModel.getCustomerNumber().toString());
        holder.customerListItemBinding.listCustomerBatteryBarcode.setText(customerModel.getBatteryBarcode());
        holder.customerListItemBinding.listCustomerSellingDate.setText(customerModel.getSellingDate());
    }

    @Override
    public int getItemCount() {
        return customerModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CustomerListItemBinding customerListItemBinding;

        public ViewHolder(CustomerListItemBinding customerListItemBinding) {
            super(customerListItemBinding.getRoot());
            this.customerListItemBinding =customerListItemBinding;
        }
    }
}
