package com.edu.unq.tpi.dapp.grupoB.Eventeando.validator;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.UserException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;

public class UserValidator extends Validator {

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
        validateNullityOf(name, new UserException(USER_IS_INVALID_WITHOUT_NAME));

        validateLenghtBetween(name, 1, 30, new UserException(USER_NAME_IS_INVALID));

        return name;
    }

    public String validateLastname(String lastname) {
        validateNullityOf(lastname, new UserException(USER_IS_INVALID_WITHOUT_LASTNAME));

        validateLenghtBetween(lastname, 1, 30, new UserException(USER_LASTNAME_IS_INVALID));

        return lastname;
    }

    public String validateEmail(String email) {
        validateNullityOf(email, new UserException(USER_IS_INVALID_WITHOUT_EMAIL));

        validateThatEmailIsCorrect(email);

        return email;
    }

    private String validateThatEmailIsCorrect(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException error) {
            throw new UserException(USER_EMAIL_IS_INVALID + ": " + error.getMessage());
        }
        return email;
    }

    public String validatePassword(String password) {
        validateNullityOf(password, new UserException(USER_IS_INVALID_WITHOUT_PASSWORD));

        validateLenghtBetween(password, 4, 10, new UserException(USER_PASSWORD_IS_INVALID));

        return password;
    }

    public LocalDate validateBirthday(LocalDate birthday) { return validateNullityOf(birthday, new UserException(USER_IS_INVALID_WITHOUT_BIRTHDAY)); }
}

