package com.example.comp5216_petloversapp;

public class UserInfo {

    private String email;
    private String userName;

    public UserInfo() {
    }

    public UserInfo(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
