package com.kpi.lab4.utils;

import com.kpi.lab4.exception.BadEmailException;
import com.kpi.lab4.exception.BadUsernameException;

public class Validator {
    public static String usernameRegex = "[A-Za-z0-9_]+";
    public static String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    public static void validateUsername(String username) throws BadUsernameException {
        if (!username.matches(usernameRegex)) {
            throw new BadUsernameException();
        }
    }

    public static void validateEmail(String email) throws BadEmailException {
        if (!email.matches(emailRegex)) {
            throw new BadEmailException();
        }
    }
}
