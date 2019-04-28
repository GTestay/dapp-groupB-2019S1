package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class LoanPayment extends MoneyTransaction {

    private double value = 0.00;

    public static LoanPayment create(User user) {
        LoanPayment instance = new LoanPayment();

        validateInstance(user, LocalDate.now(), instance);

        return instance;
    }

    @Override
    public double transactionalValue() {
        if(value == 0.00){ value = -Moneylender.LOAN_PAYMENT_COST; }

        return value;
    }
}
