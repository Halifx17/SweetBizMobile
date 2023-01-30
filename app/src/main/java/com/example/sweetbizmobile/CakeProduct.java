package com.example.sweetbizmobile;

public class CakeProduct {

    String category, imageURL, name, description, size, icing, decorations;
    Long itemno, price, quantity, totalPrice;
    int amount, icingPrice, sizePrice, decorationsPrice;

    public CakeProduct() {
    }

    public CakeProduct(String category, String imageURL, String name, String description, String size, String icing, String decorations, Long itemno, Long price, Long quantity, Long totalPrice, int amount, int icingPrice, int sizePrice, int decorationsPrice) {
        this.category = category;
        this.imageURL = imageURL;
        this.name = name;
        this.description = description;
        this.size = size;
        this.icing = icing;
        this.decorations = decorations;
        this.itemno = itemno;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.amount = amount;
        this.icingPrice = icingPrice;
        this.sizePrice = sizePrice;
        this.decorationsPrice = decorationsPrice;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIcing() {
        return icing;
    }

    public void setIcing(String icing) {
        this.icing = icing;
    }

    public String getDecorations() {
        return decorations;
    }

    public void setDecorations(String decorations) {
        this.decorations = decorations;
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

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIcingPrice() {
        return icingPrice;
    }

    public void setIcingPrice(int icingPrice) {
        this.icingPrice = icingPrice;
    }

    public int getSizePrice() {
        return sizePrice;
    }

    public void setSizePrice(int sizePrice) {
        this.sizePrice = sizePrice;
    }

    public int getDecorationsPrice() {
        return decorationsPrice;
    }

    public void setDecorationsPrice(int decorationsPrice) {
        this.decorationsPrice = decorationsPrice;
    }
}
