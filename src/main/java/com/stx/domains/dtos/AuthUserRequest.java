package com.stx.domains.dtos;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

public class AuthUserRequest {
    @NonNull
    private String username;
    @NotNull
    private String password;

    public AuthUserRequest(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
