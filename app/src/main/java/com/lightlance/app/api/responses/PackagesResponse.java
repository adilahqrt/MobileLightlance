package com.lightlance.app.api.responses;

import com.google.gson.annotations.SerializedName;
import com.lightlance.app.api.model.PackagesItem;

import java.util.List;

public class PackagesResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("packages")
    private List<PackagesItem> packages;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<PackagesItem> getPackages() {
        return packages;
    }
}