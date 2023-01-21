package com.example.sweetbizmobile;

public class OrdersAdmin {

    String customername, dateproccessed, product, status, userID, lastupdated, or, receivedby;
    Long orderno, date;
    int revenue;

    public OrdersAdmin() {
    }

    public OrdersAdmin(String customername, String dateproccessed, String product, String status, String userID, String lastupdated, String or, String receivedby, Long orderno, Long date, int revenue) {
        this.customername = customername;
        this.dateproccessed = dateproccessed;
        this.product = product;
        this.status = status;
        this.userID = userID;
        this.lastupdated = lastupdated;
        this.or = or;
        this.receivedby = receivedby;
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
