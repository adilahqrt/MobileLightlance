package com.lightlance.app.api.model;

public class PaymentModel {
    private int orderId;
    private int userId;

    public PaymentModel(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
    }
}
