package com.example.sweetbizmobile;

public class Products {
    String category, imageURL, name, description;
    Long itemno, price, quantity;

    public Products() {
    }

    public Products(String category, String imageURL, String name, String description, Long itemno, Long price, Long quantity) {
        this.category = category;
        this.imageURL = imageURL;
        this.name = name;
        this.description = description;
        this.itemno = itemno;
        this.price = price;
        this.quantity = quantity;
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
}
