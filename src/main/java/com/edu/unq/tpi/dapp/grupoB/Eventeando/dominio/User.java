package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.UserValidator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String lastname;
    private String email;
    private String password;
    private LocalDate birthday;
    private AccountManager accountManager;
    private Moneylender moneyLender;
    private List<Invitation> invitations;

    public static User create(String name, String lastname, String email, String password, LocalDate birthday) {
        User instance = new User();
        UserValidator validator = new UserValidator();

        instance.name = validator.validateName(name);
        instance.lastname = validator.validateLastname(lastname);
        instance.email = validator.validateEmail(email);
        instance.password = validator.validatePassword(password);
        instance.birthday = validator.validateBirthday(birthday);

        instance.accountManager = AccountManager.get(instance);
        instance.moneyLender = Moneylender.get();

        instance.invitations = new ArrayList<>();
        return instance;
    }

    public String name() { return name; }

    public String lastname() { return lastname; }

    public String email() { return email; }

    public String password() { return password; }

    public LocalDate birthday() { return birthday; }

    public double balance() { return accountManager.balance(this); }

    public void takeCash(double amount) { accountManager.takeCash(this, amount); }

    public void cashDeposit(double amount) { accountManager.cashDeposit(this, amount); }

    public void requireCredit(double amount) { accountManager.requireCredit(this, amount); }

    public void creditDeposit(double amount, YearMonth dueDate, Long cardNumber) { accountManager.creditDeposit(this, amount, dueDate, cardNumber) ; }

    public void takeOutALoan() { moneyLender.giveLoan(this); }

    public boolean isDefaulter() { return moneyLender.isDefaulter(this); }

    public void payLoan() { moneyLender.payLoan(this); }

    public boolean hasThisEmail(String anEmail) {
        return email.equals(anEmail);
    }

    public List<Invitation> invitations() {
        return invitations;
    }

    public void receiveEventInvitation(Invitation invitation) {
        this.invitations.add(invitation);
    }
}
