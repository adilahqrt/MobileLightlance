package com.lightlance.app.api.model;

import com.google.gson.annotations.SerializedName;

public class RegisterUser {
    private String email;
    private String password;
    @SerializedName("fullname")
    private String fullName;
    private String gender;
    private String address;
    private String phone;
    private int balance;

    public RegisterUser(String email, String password, String fullName, String gender, String address, String phone, int balance) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.balance = balance;
    }
}
