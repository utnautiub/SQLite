package com.example.btt_sqlite_vinfast.model;

public class Cart {
    private int id;
    private int userId;
    private String createdAt;


    // Constructor mặc định không tham số
    public Cart() {
    }

    // Constructor
    public Cart(int id, int userId, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}