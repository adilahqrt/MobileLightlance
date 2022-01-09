package com.lightlance.app.api.responses;

import com.google.gson.annotations.SerializedName;
import com.lightlance.app.api.model.Receipt;

public class PaymentResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("receipt")
	private Receipt receipt;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public Receipt getReceipt(){
		return receipt;
	}

	public String getMessage(){
		return message;
	}
}