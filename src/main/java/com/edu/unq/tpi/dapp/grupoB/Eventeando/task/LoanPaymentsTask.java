package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;

public class LoanPaymentsTask {
    public void execute(Moneylender moneyLender, AccountManager accountManager) {
        moneyLender.actualLoans().forEach(loan -> payIfUnpaid(moneyLender, loan, accountManager));

        moneyLender.checkIfLoanIsOver(accountManager);
    }

    private void payIfUnpaid(Moneylender moneyLender, Loan loan, AccountManager accountManager) {
        if(moneyLender.remainingPayments(loan.user(), accountManager) > 0) {
            loan.user().payLoan(accountManager, moneyLender);
        }
    }
}
