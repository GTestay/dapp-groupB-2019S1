package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.UserException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;

public class UserValidator {

    public static final String USER_IS_INVALID_WITHOUT_NAME = "User Is Invalid Without Name";
    public static final String USER_IS_INVALID_WITHOUT_LASTNAME = "User Is Invalid Without LastName";
    public static final String USER_NAME_IS_INVALID = "The Name Has An Invalid Format";
    public static final String USER_LASTNAME_IS_INVALID = "The Lastname Has An Invalid Format";
    public static final String USER_IS_INVALID_WITHOUT_EMAIL = "User Is Invalid Without Email";
    public static final String USER_EMAIL_IS_INVALID = "The Email Has An Invalid Format";
    public static final String USER_IS_INVALID_WITHOUT_PASSWORD = "User Is Invalid Without Password";
    public static final String USER_IS_INVALID_WITHOUT_BIRTHDAY = "User Is Invalid Without Birthday";
    public static final String USER_PASSWORD_IS_INVALID = "The Password Has An Invalid Format";

    public String validateName(String name) {
        if (name == null) { throw new UserException(USER_IS_INVALID_WITHOUT_NAME); }

        int nameLength = name.length();
        if (nameLength > 30 || nameLength < 1) { throw new UserException(USER_NAME_IS_INVALID); }

        return name;
    }

    public String validateLastname(String lastname) {
        if (lastname == null) { throw new UserException(USER_IS_INVALID_WITHOUT_LASTNAME); }

        int lastnameLength = lastname.length();
        if (lastnameLength > 30 || lastnameLength < 1) { throw new UserException(USER_LASTNAME_IS_INVALID); }

        return lastname;
    }

    public String validateEmail(String email) {
        if (email == null) { throw new UserException(USER_IS_INVALID_WITHOUT_EMAIL); }

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException error) {
            throw new UserException(USER_EMAIL_IS_INVALID);
        }

        return email;
    }

    public String validatePassword(String password) {
        if (password == null) { throw new UserException(USER_IS_INVALID_WITHOUT_PASSWORD); }

        int passwordLength = password.length();
        if (passwordLength < 4 || passwordLength > 10) { throw new UserException(USER_PASSWORD_IS_INVALID); }

        return password;
    }

    public LocalDate validateBirthday(LocalDate birthday) {
        if (birthday == null) { throw new UserException(USER_IS_INVALID_WITHOUT_BIRTHDAY); }

        return birthday;
    }
}

