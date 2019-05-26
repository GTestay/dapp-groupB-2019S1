package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@DiscriminatorValue("BaquitaCrowdFunding")
public class BaquitaCrowdFundingEvent extends Event {

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private final SharedAccount sharedAccount = new SharedAccount();

    @JsonCreator
    public static BaquitaCrowdFundingEvent create(User organizer, String description, List<User> guests, List<Expense> expenses) {
        return validateEvent(new BaquitaCrowdFundingEvent(), organizer, description, expenses, guests);
    }

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
