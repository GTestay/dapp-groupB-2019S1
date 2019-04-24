package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.UserValidator;
import java.time.LocalDate;

public class User {

    private String name;
    private String lastname;
    private String email;
    private String password;
    private LocalDate birthday;
    private AccountManager accountManager;

    public static User create(String name, String lastname, String email, String password, LocalDate birthday) {
        User instance = new User();
        UserValidator validator = new UserValidator();

        instance.name = validator.validateName(name);
        instance.lastname = validator.validateLastname(lastname);
        instance.email = validator.validateEmail(email);
        instance.password = validator.validatePassword(password);
        instance.birthday = validator.validateBirthday(birthday);

        instance.accountManager = AccountManager.get(instance);

        return instance;
    }

    public String name() { return name; }

    public String lastname() { return lastname; }

    public String email() { return email; }

    public String password() { return password; }

    public LocalDate birthday() { return birthday; }

    public double statement() { return accountManager.statement(this); }

    public void takeCash(double amount) { accountManager.takeCash(this, amount); }

    public void cashDeposit(double amount) { accountManager.cashDeposit(this, amount); }

    public void requireCredit(double amount) { accountManager.requireCredit(this, amount); }

    public void creditDeposit(double amount) { accountManager.creditDeposit(this, amount) ; }

    public boolean hasEmail(String anEmail) {
        return email == anEmail;
    }
}
