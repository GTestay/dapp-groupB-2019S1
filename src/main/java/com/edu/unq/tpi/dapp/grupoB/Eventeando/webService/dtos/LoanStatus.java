package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class LoanStatus {

    @JsonProperty
    private final int remainingFees;
    @JsonProperty
    private final LocalDate date;
    @JsonProperty
    private final String email;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final boolean defaulter;

    public LoanStatus(Loan loan, MoneylenderService moneylenderService, AccountManagerService accountManagerService) {
        User user = loan.user();

        this.email = user.email();
        this.name = user.fullName();
        this.defaulter = user.isDefaulter(moneylenderService);
        this.remainingFees = moneylenderService.remainingPayments(loan, accountManagerService);
        this.date = loan.date();
    }
}
