package com.kpi.lab4.entities;

import com.kpi.lab4.enums.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    private String sessionId;
    private UUID userId;
    private UserType role;
    private LocalDateTime validUntil;
    private String username;
    private String csrfToken;

    public Session(String sessionId, UUID userId, UserType role, LocalDateTime validUntil, String username, String csrfToken) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.role = role;
        this.validUntil = validUntil;
        this.username = username;
        this.csrfToken = csrfToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public UserType getRole() {
        return role;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public void setRole(UserType role) {
        this.role = role;
    }
}
