package com.example.warrantycheckerforretailer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantycheckerforretailer.databinding.CustomerListItemBinding;
import com.example.warrantycheckerforretailer.models.CustomerModel;

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
