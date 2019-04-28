package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.time.LocalDateTime;
import java.util.List;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE;

public class Party extends Event {

    protected LocalDateTime invitationLimitDate;
    protected Double pricePerAssistant;
    protected List<String> guestConfirmations;


    public LocalDateTime invitationLimitDate() {
        return invitationLimitDate;
    }

    public void addExpense(String supplyName, Double supplyPrice) {
        if (supplyPrice < 0) {
            throw new RuntimeException(ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE);
        }
        expenses.put(supplyName, supplyPrice);
    }

    public void confirmAssistance(String anEmail) {
        validateThatTheUserWasInvited(anEmail);
        guestConfirmations.add(anEmail);
    }

    public Double totalCost() {
        return expensesCostPerAssistant() + costPerConfirmedAssistance();
    }

    private double expensesCostPerAssistant() {
        return quantityOfConfirmations() * expensesTotalCost();
    }

    private Double costPerConfirmedAssistance() {
        return pricePerAssistant * this.quantityOfConfirmations();
    }

    private Integer quantityOfConfirmations() {
        return guestConfirmations.size();
    }

    public boolean guestHasConfirmed(User guest) {
        return guestConfirmations.stream().anyMatch(guest::hasThisEmail);
    }

}
