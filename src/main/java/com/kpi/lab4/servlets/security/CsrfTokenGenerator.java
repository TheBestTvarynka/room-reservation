package com.kpi.lab4.servlets.security;

import java.security.SecureRandom;

public class CsrfTokenGenerator implements TokenGenerator {
    @Override
    public String generateToken() {
        SecureRandom random = new SecureRandom();
        return random
                .ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(64)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
