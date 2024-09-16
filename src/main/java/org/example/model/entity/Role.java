package org.example.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    WORKER,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
