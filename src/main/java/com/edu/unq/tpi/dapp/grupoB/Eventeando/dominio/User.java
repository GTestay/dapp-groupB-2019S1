package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer.UserDeserializer;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer.UserSerializer;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.UserValidator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonSerialize(using = UserSerializer.class)
@JsonDeserialize(using = UserDeserializer.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String password;
    private LocalDate birthday;

    @Column(unique = true)
    private String email;

    @OneToMany
    private List<Invitation> invitations;

    private boolean indebt = false;

    public static User create(String name, String lastname, String email, String password, LocalDate birthday) {
        User instance = new User();
        UserValidator validator = new UserValidator();

        instance.name = validator.validateName(name);
        instance.lastname = validator.validateLastname(lastname);
        instance.email = validator.validateEmail(email);
        instance.password = validator.validatePassword(password);
        instance.birthday = validator.validateBirthday(birthday);

        instance.invitations = new ArrayList<>();
        return instance;
    }

    public String name() { return name; }

    public String lastname() { return lastname; }

    public String email() { return email; }

    public String password() { return password; }

    public LocalDate birthday() { return birthday; }

    public double balance(AccountManagerService accountManagerService) { return accountManagerService.balance(this); }

    public void takeCash(double amount, AccountManagerService accountManagerService) { accountManagerService.takeCash(this, amount); }

    public void cashDeposit(double amount, AccountManagerService accountManagerService) { accountManagerService.cashDeposit(this, amount); }

    public void requireCredit(double amount, AccountManagerService accountManagerService) { accountManagerService.requireCredit(this, amount); }

    public void creditDeposit(double amount, YearMonth dueDate, String cardNumber, AccountManagerService accountManagerService) { accountManagerService.creditDeposit(this, amount, dueDate, cardNumber) ; }

    public Loan takeOutALoan(MoneylenderService moneyLender, AccountManagerService accountManagerService) {

        return moneyLender.giveLoan(this, accountManagerService);
    }

    public boolean isDefaulter(MoneylenderService moneyLender) { return moneyLender.isDefaulter(this); }

    public void payLoan(MoneylenderService moneyLender, AccountManagerService accountManagerService) { moneyLender.payLoan(this, moneyLender, accountManagerService); }

    public boolean hasThisEmail(String anEmail) {
        return email.equals(anEmail);
    }

    public List<Invitation> invitations() {
        return invitations;
    }

    public void receiveEventInvitation(Invitation invitation) {
        this.invitations.add(invitation);
    }

    public Long id() {
        return id;
    }

    public String fullName() {
        return this.name + " " + this.lastname;
    }

    public void withDebt() {
        indebt = true;
    }

    public boolean indebt() {
        return this.indebt;
    }

    public void payDebt() {
        indebt = false;
    }
}
