package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Loan")
public class Loan extends MoneyTransaction {
    @JsonProperty
    private boolean ended = false;

    public static Loan create(User user) {
        Loan instance = new Loan();

        validateInstance(user, LocalDate.now(), MoneylenderService.LOAN_COST, instance);

        return instance;
    }

    @Override
    public double transactionalValue() { return amount; }

    public boolean isOwner(User owner) { return owner == user; }

    public void end() {
        ended = true;
    }

    public boolean isEnded() {
        return ended;
    }

    public void payIfUnpaid(MoneylenderService moneyLenderService, AccountManagerService accountManagerService) {
        if(moneyLenderService.remainingPayments(this, accountManagerService) > 0) {
            user.payLoan(moneyLenderService, accountManagerService);

            moneyLenderService.checkIfLoanIsOver(this, accountManagerService);
        }
    }
}
