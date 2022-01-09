package com.lightlance.app.api.responses;

import com.google.gson.annotations.SerializedName;

public class TopUpResponse {
    private String status;
    @SerializedName("topupNominal")
    private int topUpNominal;
    private int balance;
}
