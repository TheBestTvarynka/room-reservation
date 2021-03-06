package com.kpi.lab4.dto;

import com.kpi.lab4.enums.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionDto {
    private String sessionId;
    private UUID userId;
    private LocalDateTime validUntil;
    private UserType role;
    private String username;
    private String csrfToken;

    public SessionDto(String sessionId, UUID userId, UserType role, String username, String csrfToken) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.csrfToken = csrfToken;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
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
