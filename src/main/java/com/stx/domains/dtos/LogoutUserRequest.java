package com.stx.domains.dtos;

import com.sun.istack.NotNull;

import java.util.UUID;

public class LogoutUserRequest {
    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
