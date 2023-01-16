package com.example.sweetbizmobile;

public class OrdersAdmin {

    String customername, date, dateproccessed, product, status, userID, lastupdated, or, receivedby;
    Long orderno;
    int revenue;

    public OrdersAdmin() {
    }

    public OrdersAdmin(String customername, String date, String dateproccessed, String product, String status, String userID, String lastupdated, String or, String receivedby, Long orderno, int revenue) {
        this.customername = customername;
        this.date = date;
        this.dateproccessed = dateproccessed;
        this.product = product;
        this.status = status;
        this.userID = userID;
        this.lastupdated = lastupdated;
        this.or = or;
        this.receivedby = receivedby;
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

    public String getDateproccessed() {
        return dateproccessed;
    }

    public void setDateproccessed(String dateproccessed) {
        this.dateproccessed = dateproccessed;
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

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getOr() {
        return or;
    }

    public void setOr(String or) {
        this.or = or;
    }

    public String getReceivedby() {
        return receivedby;
    }

    public void setReceivedby(String receivedby) {
        this.receivedby = receivedby;
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
