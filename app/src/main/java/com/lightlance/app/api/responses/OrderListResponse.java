package com.lightlance.app.api.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderListResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("orders")
	private List<OrdersItem> orders;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public List<OrdersItem> getOrders(){
		return orders;
	}

	public String getMessage(){
		return message;
	}
}