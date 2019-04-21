package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class DepositByCreditCard extends Deposit {
    public static DepositByCreditCard create(User user, LocalDate date, double amount) {
        DepositByCreditCard instance = new DepositByCreditCard();

        madeValidations(user, date, amount, instance);

        return instance;
    }
}
