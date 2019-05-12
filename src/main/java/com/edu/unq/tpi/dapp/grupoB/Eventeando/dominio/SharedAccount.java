package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT;
import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_AMOUNT_IS_INVALID;


@Entity
public class SharedAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double funds = 0.0;
    @Transient
    private List<User> guestsWhoPaid;

    public SharedAccount() {
        guestsWhoPaid = new ArrayList<>();
    }

    public Double actualFunds() {
        return funds;
    }

    void validateThatTheAmountNotSurpassRequiredFunds(Double amount, BaquitaCrowdFundingEvent baquitaCrowdFundingEvent) {
        if (actualFunds() + amount > baquitaCrowdFundingEvent.totalCost()) {
            baquitaCrowdFundingEvent.throwEventException(ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT);
        }
    }

    void validateThatTheAmountIsValid(Double amount, BaquitaCrowdFundingEvent baquitaCrowdFundingEvent) {
        if (amount <= 0) {
            baquitaCrowdFundingEvent.throwEventException(ERROR_THE_AMOUNT_IS_INVALID);
        }
    }

    void addFunds(User guest, Double amount, BaquitaCrowdFundingEvent baquitaCrowdFundingEvent) {
        validateThatTheAmountNotSurpassRequiredFunds(amount, baquitaCrowdFundingEvent);
        validateThatTheAmountIsValid(amount, baquitaCrowdFundingEvent);
        guestsWhoPaid.add(guest);
        this.funds += amount;
    }

    public boolean hasAddedFunds(User user) {
        return guestsWhoPaid.contains(user);
    }
}