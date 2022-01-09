package com.lightlance.app.api.responses;

import com.lightlance.app.api.model.CategoriesItem;

import java.util.List;

public class CategoriesResponse{
	private boolean success;
	private List<CategoriesItem> categories;
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public String getMessage(){
		return message;
	}
}