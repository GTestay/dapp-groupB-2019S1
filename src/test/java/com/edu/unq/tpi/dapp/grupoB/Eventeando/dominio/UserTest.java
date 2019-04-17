package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.UserException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.UserValidator;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class UserTest {

    @Test
    public void creationOfANewUser() {
        User user = User.create("Maximo", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));

        assertEquals("Maximo", user.name());
        assertEquals("Cossetti", user.lastname());
        assertEquals("eravenna@gmail.com", user.email());
        assertEquals("S1M6L4R", user.password());
        assertEquals(LocalDate.of(2002, 3, 21), user.birthday());
    }

    @Test
    public void userValidations(){
        nameValidations();

        lastnameValidations();

        emailValidations();

        passwordValidations();

        birthdayValidations();
    }

    private void birthdayValidations() {
        try {
            User.create("Maximo", "Cossetti", "eravenna@gmail.com", "S1M6L4R", null);
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_BIRTHDAY);
        }
    }

    private void passwordValidations() {
        try {
            User.create("Maximo", "Cossetti", "eravenna@gmail.com", null, LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_PASSWORD);
        }

        try {
            User.create("Maximo", "Cosseti", "eravenna@gmail.com", "L0s S1m6l4d0r3s", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_PASSWORD_IS_INVALID);
        }

        try {
            User.create("Maximo", "Cosseti", "eravenna@gmail.com", "S1M", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_PASSWORD_IS_INVALID);
        }
    }

    private void emailValidations() {
        try {
            User.create("Maximo", "Cossetti", null, "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_EMAIL);
        }

        try {
            User.create("Maximo", "Cossetti", "eravennagmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_EMAIL_IS_INVALID + ": Missing final '@domain'");
        }
    }

    private void lastnameValidations() {
        try {
            User.create("Maximo", null, "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_LASTNAME);
        }

        try {
            User.create("Maximo", "UnApellidoDemasiadoLargoComoParaSerReal", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_LASTNAME_IS_INVALID);
        }

        try {
            User.create("Maximo", "", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_LASTNAME_IS_INVALID);
        }
    }

    private void nameValidations() {
        try {
            User.create(null, "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_NAME);
        }

        try {
            User.create("UnNombreDemasiadoLargoComoParaSerReal", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_NAME_IS_INVALID);
        }

        try {
            User.create("", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_NAME_IS_INVALID);
        }
    }
}
