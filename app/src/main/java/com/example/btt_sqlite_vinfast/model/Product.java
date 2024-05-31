package com.example.btt_sqlite_vinfast.model;

public class Product {
    private int id;
    private String code;
    private String name;
    private String imageUrl;
    private double price;
    private int categoryId;

    // Constructor
    public Product(int id, String code, String name, String imageUrl, double price, int categoryId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.categoryId = categoryId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
