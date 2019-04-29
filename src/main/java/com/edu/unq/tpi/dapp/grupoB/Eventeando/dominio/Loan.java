package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class Loan extends MoneyTransaction {

    private double value = 0.00;

    public static Loan create(User user) {
        Loan instance = new Loan();

        validateInstance(user, LocalDate.now(), instance);

        return instance;
    }

    @Override
    public double transactionalValue() {
        if(value == 0.00){ value = Moneylender.LOAN_COST; }

        return value;
    }

    public boolean isOwner(User owner) { return owner == user; }
}
