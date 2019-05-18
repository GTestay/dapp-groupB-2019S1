package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;

public class BaquitaCrowdFundingEvent extends Event {

    private final SharedAccount sharedAccount = new SharedAccount();

    public Double totalMoneyRaised() {
        return sharedAccount.actualFunds();
    }

    public void addFunds(User guest, Double amount) {
        validateThatTheUserWasInvited(guest.email());
        validateThatTheEventIsNotFullyFunded();
        sharedAccount.addFunds(guest, amount, this);
    }

    private void validateThatTheEventIsNotFullyFunded() {
        if (isFund()) {
            throwEventException(EventValidator.ERROR_CAN_NOT_ADD_MORE_MONEY_THE_CROWDFUNDING_EVENT_IS_FUNDED);
        }
    }

    public boolean isFund() {
        return sharedAccount.actualFunds().equals(this.totalCost());
    }

    public boolean hasAddedFunds(User user) {
        return sharedAccount.hasAddedFunds(user);
    }
}
