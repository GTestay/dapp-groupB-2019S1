package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;

public class LoanPaymentsTask {
    public void execute(Moneylender moneyLender, AccountManager accountManager) {
        moneyLender.actualLoans().forEach(loan -> payIfUnpaid(loan, moneyLender, accountManager));

        moneyLender.checkIfLoanIsOver(accountManager);
    }

    private void payIfUnpaid(Loan loan, Moneylender moneyLender, AccountManager accountManager) {
        if(moneyLender.remainingPayments(loan, accountManager) > 0) {
            loan.user().payLoan(moneyLender, accountManager);
        }
    }
}
