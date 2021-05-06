package com.example.libararymanager.Model;

import com.google.firebase.Timestamp;

public class UserModel {
    String userId, name, phone;
    Timestamp timestamp;
    boolean isAdmin;

    public UserModel() {
    }

    public UserModel(String userId, String name, String phone, Timestamp timestamp, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.timestamp = timestamp;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
