package com.lightlance.app.api.responses;

import com.lightlance.app.api.model.User;

public class LoginResponse {
    private boolean success;
    private String message;
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
