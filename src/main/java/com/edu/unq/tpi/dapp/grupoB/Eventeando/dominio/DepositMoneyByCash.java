package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class DepositMoneyByCash extends DepositMoney {
    public static DepositMoneyByCash create(User user, LocalDate date, double amount) {
        DepositMoneyByCash instance = new DepositMoneyByCash();

        madeValidations(user, date, amount, instance);

        return instance;
    }
}
