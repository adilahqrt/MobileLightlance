package com.lightlance.app.api.responses;

public class OrderResponse {
    private boolean success;
    private String message;
    private int idTransaction;

    public OrderResponse(boolean success, String message, int idTransaction) {
        this.success = success;
        this.message = message;
        this.idTransaction = idTransaction;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getIdTransaction() {
        return idTransaction;
    }
}
