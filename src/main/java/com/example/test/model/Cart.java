package com.example.test.model;

public class Cart {
    private String name;
    private Double price;
    private String image;
    private Integer count;
    private String owner;

    public Cart(String name, Double price, String image, Integer count, String owner) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.count = count;
        this.owner = owner;
    }

    public Cart() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
