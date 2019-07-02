package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;

public class LoanPaymentsTask {
    public void execute(MoneylenderService moneylenderService, AccountManagerService accountManagerService) {
        moneylenderService.actualLoans().forEach(loan -> loan.payIfUnpaid(moneylenderService, accountManagerService));
    }
}
