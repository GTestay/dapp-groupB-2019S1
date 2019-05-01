package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT;
import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_AMOUNT_IS_INVALID;

public class BaquitaCrowdFundingEvent extends Event {

    private Double funds = 0.0;

    public double totalFunds() {
        return funds;
    }

    public void addFunds(User guest, Double amount) {
        validateThatTheUserWasInvited(guest.email());
        validateThatTheEventIsNotFullyFunded();
        validateThatTheAmountNotSurpassRequiredFunds(amount);
        validateThatTheAmountIsValid(amount);

        funds += amount;
    }

    private void validateThatTheAmountIsValid(Double amount) {
        if (amount <= 0) {
            throwEventException(ERROR_THE_AMOUNT_IS_INVALID);
        }
    }

    private void validateThatTheAmountNotSurpassRequiredFunds(Double amount) {
        if (funds + amount > totalCost()) {
            throwEventException(ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT);
        }

    }

    private void validateThatTheEventIsNotFullyFunded() {
        if (funds.equals(this.totalCost())) {
            throwEventException(EventValidator.ERROR_CAN_NOT_ADD_MORE_MONEY_THE_CROWDFUNDING_EVENT_IS_FUNDED);
        }
    }

    public boolean isFund() {
        return funds.equals(this.totalCost());
    }
}
