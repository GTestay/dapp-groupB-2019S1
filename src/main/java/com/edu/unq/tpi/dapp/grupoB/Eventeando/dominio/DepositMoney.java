package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.DepositMoneyValidator;

import java.time.LocalDate;

public class DepositMoney {
    protected User user;
    protected LocalDate date;
    protected double amount;

    protected static void madeValidations(User user, LocalDate date, double amount, DepositMoney instance) {
        DepositMoneyValidator validator = new DepositMoneyValidator();

        instance.user = validator.validateUser(user);
        instance.date = validator.validateDate(date);
        instance.amount = validator.validateAmount(amount);
    }

    public User user() { return user; }

    public LocalDate date() { return date; }

    public double amount() { return amount; }
}
