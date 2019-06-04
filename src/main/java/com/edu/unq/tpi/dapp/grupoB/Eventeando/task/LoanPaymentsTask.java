package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;

public class LoanPaymentsTask {
    public void execute() {
        Moneylender moneyLender = Moneylender.get();

        moneyLender.actualLoans().forEach(loan -> payIfUnpaid(moneyLender, loan));

        moneyLender.checkIfLoanIsOver();
    }

    private void payIfUnpaid(Moneylender moneyLender, Loan loan) {
        if(moneyLender.remainingPayments(loan.user()) > 0) {
            loan.user().payLoan();
        }
    }
}
