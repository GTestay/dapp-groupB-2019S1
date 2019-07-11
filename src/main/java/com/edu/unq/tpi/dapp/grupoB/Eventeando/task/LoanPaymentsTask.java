package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;

public class LoanPaymentsTask {
    public void execute(MoneylenderService moneyLender, AccountManagerService accountManagerService) {
        moneyLender.actualLoans().forEach(loan -> payIfUnpaid(loan, moneyLender, accountManagerService));

        moneyLender.checkIfLoanIsOver(accountManagerService);
    }

    private void payIfUnpaid(Loan loan, MoneylenderService moneyLender, AccountManagerService accountManagerService) {
        if(moneyLender.remainingPayments(loan, accountManagerService) > 0) {
            loan.user().payLoan(moneyLender, accountManagerService);
        }
    }
}
