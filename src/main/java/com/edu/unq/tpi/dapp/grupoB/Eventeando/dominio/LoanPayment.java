package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class LoanPayment extends MoneyTransaction {

    private double value = 0.00;
    private Loan referenceLoan;

    public static LoanPayment create(User user, Loan referenceLoan) {
        LoanPayment instance = new LoanPayment();

        validateInstance(user, LocalDate.now(), Moneylender.LOAN_PAYMENT_COST, instance);

        instance.referenceLoan = referenceLoan;

        return instance;
    }

    @Override
    public double transactionalValue() {
        if(value == 0.00){ value = -Moneylender.LOAN_PAYMENT_COST; }

        return value;
    }

    public boolean belongsTo(Loan loan) { return referenceLoan == loan; }
}
