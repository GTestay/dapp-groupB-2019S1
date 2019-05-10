package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDate;

public class LoanPayment extends MoneyTransaction {

    private Loan referenceLoan;

    public static LoanPayment create(User user, Loan referenceLoan) {
        LoanPayment instance = new LoanPayment();

        validateInstance(user, LocalDate.now(), Moneylender.LOAN_PAYMENT_COST, instance);

        instance.referenceLoan = referenceLoan;

        return instance;
    }

    @Override
    public double transactionalValue() { return -amount; }

    public boolean belongsTo(Loan loan) { return referenceLoan == loan; }
}
