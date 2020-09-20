package com.love.start.dto;

import com.love.start.entity.Account;

import java.util.Calendar;

public class AccountAuthDTO {
    private String username;
    private String password;

    public AccountAuthDTO() {

    }

    public AccountAuthDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
