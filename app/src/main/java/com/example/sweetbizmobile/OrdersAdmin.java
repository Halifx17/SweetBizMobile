package com.example.sweetbizmobile;

public class OrdersAdmin {

    String customername, date, product, status, userID;
    Long orderno;
    int revenue;

    public OrdersAdmin() {
    }

    public OrdersAdmin(String customername, String date, String product, String status, String userID, Long orderno, int revenue) {
        this.customername = customername;
        this.date = date;
        this.product = product;
        this.status = status;
        this.userID = userID;
        this.orderno = orderno;
        this.revenue = revenue;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getOrderno() {
        return orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}