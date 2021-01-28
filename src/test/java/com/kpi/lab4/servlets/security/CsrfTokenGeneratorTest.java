package com.kpi.lab4.servlets.security;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CsrfTokenGeneratorTest {
    @Test
    public void tokenGenerationExample() {
        TokenGenerator tokenGenerator = new CsrfTokenGenerator();
        System.out.println(tokenGenerator.generateToken());
        System.out.println(tokenGenerator.generateToken());
        System.out.println(tokenGenerator.generateToken());
        System.out.println(tokenGenerator.generateToken());
        System.out.println(tokenGenerator.generateToken());
        System.out.println(tokenGenerator.generateToken());
        assertTrue(true);
    }
}
