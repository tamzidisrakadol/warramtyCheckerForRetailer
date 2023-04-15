package com.example.warrantycheckerforretailer.models;

public class CustomerModel {
    int id;
    String batteryBarcode,sellingDate,expireDate;

    public CustomerModel(int id, String batteryBarcode, String sellingDate, String expireDate) {
        this.id = id;
        this.batteryBarcode = batteryBarcode;
        this.sellingDate = sellingDate;
        this.expireDate = expireDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatteryBarcode() {
        return batteryBarcode;
    }

    public void setBatteryBarcode(String batteryBarcode) {
        this.batteryBarcode = batteryBarcode;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
