package com.example.test.model;

public class Product {
    private String name;
    private String price;
    private String details;
    private String image;

    public Product(String name, String price, String details, String image) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.image = image;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
