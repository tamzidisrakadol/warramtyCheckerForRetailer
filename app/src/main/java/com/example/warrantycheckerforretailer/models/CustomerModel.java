package com.example.warrantycheckerforretailer.models;

public class CustomerModel {
    int cID;
    String customerName,batteryBarcode,sellingDate,expireDate;
    Long customerNumber;

    public CustomerModel(int cID, String customerName, String batteryBarcode, String sellingDate, String expireDate, Long customerNumber) {
        this.cID = cID;
        this.customerName = customerName;
        this.batteryBarcode = batteryBarcode;
        this.sellingDate = sellingDate;
        this.expireDate = expireDate;
        this.customerNumber = customerNumber;
    }

    public CustomerModel() {
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }
}
