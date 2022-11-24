package com.example.sweetbizmobile;

public class CartProducts {

    String category, imageURL, name;
    Long itemno, price, quantity;
    int amount;

    public CartProducts() {
    }

    public CartProducts(String category, String imageURL, String name, Long itemno, Long price, Long quantity, int amount) {
        this.category = category;
        this.imageURL = imageURL;
        this.name = name;
        this.itemno = itemno;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getItemno() {
        return itemno;
    }

    public void setItemno(Long itemno) {
        this.itemno = itemno;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
