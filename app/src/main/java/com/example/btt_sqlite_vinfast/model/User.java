package com.example.btt_sqlite_vinfast.model;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String fullName;
    private String role;

    public User(int user_id, String username, String password, String fullName, String role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters
    public int getId() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setId(int id) {
        this.user_id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
