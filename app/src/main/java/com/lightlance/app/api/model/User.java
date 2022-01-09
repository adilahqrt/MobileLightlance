package com.lightlance.app.api.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id_user")
    private String id;
    private String email;
    @SerializedName("fullname")
    private String fullName;
    private String gender;
    @SerializedName("alamat")
    private String address;
    @SerializedName("no_telp")
    private String phone;
    @SerializedName("saldo")
    private String balance;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getBalance() {
        return balance;
    }
}
