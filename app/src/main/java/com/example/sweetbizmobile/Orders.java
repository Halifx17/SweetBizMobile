package com.example.sweetbizmobile;

public class Orders {

    String customername, product, status;
    Long orderno, date;
    int revenue;

    public Orders() {
    }

    public Orders(String customername, String product, String status, Long orderno, Long date, int revenue) {
        this.customername = customername;
        this.product = product;
        this.status = status;
        this.orderno = orderno;
        this.date = date;
        this.revenue = revenue;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
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

    public Long getOrderno() {
        return orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
