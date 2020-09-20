package com.love.start.dto;

import com.love.start.entity.Account;
import java.util.Calendar;

public class AccountDTO {
    private Long id;
    private String username;
    private String email;
    private String password;

    AccountDTO() {}

    AccountDTO(Long id, String username, String email, String password) {}

    public Account ToAccount() {
        return new Account(this.id, this.username, this.email, this.password);
    }

    public Account ToAccountCreate() {
        Long timeNow = (long) Calendar.getInstance().get(Calendar.MILLISECOND);
        return new Account(this.username, this.email, this.password, timeNow);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
