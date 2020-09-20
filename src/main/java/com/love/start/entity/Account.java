package com.love.start.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;

@Entity
@Table(name = "accounts2")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", length = 50, unique = true)
    @NotNull
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "create_at")
    private Long createAt;
    @Column(name = "update_at")
    private Long updateAt;

    public Account() {
    }

    public Account(String username, String email, String password, Long createAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
    }

    public Account(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Account(Long id, String username, String email, String password, Long updateAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.updateAt = updateAt;
    }

    public Account(Long id, String username, String email, String password, Long createAt, Long updateAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
        this.updateAt = updateAt;
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

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }
}
