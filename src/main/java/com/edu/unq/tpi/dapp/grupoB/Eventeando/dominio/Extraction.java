package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class Extraction extends MoneyTransaction {
    public static Extraction create(User user, LocalDate date, double amount) {
        Extraction instance = new Extraction();

        madeValidations(user, date, amount, instance);

        return instance;
    }

    public double transactionalValue() { return -amount; }
}
