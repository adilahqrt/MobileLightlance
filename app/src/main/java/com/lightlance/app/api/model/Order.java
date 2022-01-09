package com.lightlance.app.api.model;

public class Order {
    private String dateOrder;
    private String address;
    private int userId;
    private int packageId;

    public Order(String dateOrder, String address, int userId, int packageId) {
        this.dateOrder = dateOrder;
        this.address = address;
        this.userId = userId;
        this.packageId = packageId;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public String getAddress() {
        return address;
    }

    public int getUserId() {
        return userId;
    }

    public int getPackageId() {
        return packageId;
    }
}
