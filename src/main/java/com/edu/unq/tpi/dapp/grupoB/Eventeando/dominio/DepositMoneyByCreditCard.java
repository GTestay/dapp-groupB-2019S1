package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class DepositMoneyByCreditCard extends DepositMoney {
    public static DepositMoneyByCreditCard create(User user, LocalDate date, double amount) {
        DepositMoneyByCreditCard instance = new DepositMoneyByCreditCard();

        madeValidations(user, date, amount, instance);

        return instance;
    }
}
