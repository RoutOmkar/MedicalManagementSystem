package com.hospital.dto;

public class LoginResponse {
    private String token;
    private String userType;
    private int userId;
    private String name;
    private String email;
    private String message;
    private boolean success;

    public LoginResponse() {}

    public LoginResponse(String token, String userType, int userId, String name, String email, String message, boolean success) {
        this.token = token;
        this.userType = userType;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.message = message;
        this.success = success;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
