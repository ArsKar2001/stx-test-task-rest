package com.stx.domains.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER_ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
