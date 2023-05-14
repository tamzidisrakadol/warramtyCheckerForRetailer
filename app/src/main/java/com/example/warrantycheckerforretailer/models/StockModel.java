package com.example.warrantycheckerforretailer.models;

public class StockModel {
    private int id;
    private String vendorid,batterycode,scandate,enddate;

    public StockModel(int id, String vendorid, String batterycode, String scandate, String enddate) {
        this.id = id;
        this.vendorid = vendorid;
        this.batterycode = batterycode;
        this.scandate = scandate;
        this.enddate = enddate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getBatterycode() {
        return batterycode;
    }

    public void setBatterycode(String batterycode) {
        this.batterycode = batterycode;
    }

    public String getScandate() {
        return scandate;
    }

    public void setScandate(String scandate) {
        this.scandate = scandate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
