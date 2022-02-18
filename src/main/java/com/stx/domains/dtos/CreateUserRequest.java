package com.stx.domains.dtos;


import com.sun.istack.NotNull;

public class CreateUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String rePassword;

    public String getUsername() {
        return username;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
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
