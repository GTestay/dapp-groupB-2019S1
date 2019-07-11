package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("LoanPayment")
public class LoanPayment extends MoneyTransaction {

    @ManyToOne
    private Loan referenceLoan;

    public static LoanPayment create(User user, Loan referenceLoan) {
        LoanPayment instance = new LoanPayment();

        validateInstance(user, LocalDate.now(), MoneylenderService.LOAN_PAYMENT_COST, instance);

        instance.referenceLoan = referenceLoan;

        return instance;
    }

    @Override
    public double transactionalValue() { return -amount; }

    public boolean belongsTo(Loan loan) { return referenceLoan == loan; }
}
