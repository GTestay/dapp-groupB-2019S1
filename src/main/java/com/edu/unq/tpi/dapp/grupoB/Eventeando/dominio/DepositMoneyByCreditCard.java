package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.DepositMoneyValidator;

import java.time.LocalDate;

public class DepositMoneyByCreditCard {
    private User user;
    private LocalDate date;
    private double amount;

    public static DepositMoneyByCreditCard create(User user, LocalDate date, double amount) {
        DepositMoneyByCreditCard instance = new DepositMoneyByCreditCard();
        DepositMoneyValidator validator = new DepositMoneyValidator();

        instance.user = validator.validateUser(user);
        instance.date = validator.validateDate(date);
        instance.amount = validator.validateAmount(amount);

        return instance;
    }

    public User user() {
        return user;
    }

    public LocalDate date() {
        return date;
    }

    public double amount() {
        return amount;
    }
}
