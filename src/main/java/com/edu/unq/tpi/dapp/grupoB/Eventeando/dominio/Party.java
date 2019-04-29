package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;

import java.time.LocalDateTime;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE;
import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT;

public class Party extends Event {

    protected LocalDateTime invitationLimitDate;
    protected Double pricePerAssistant;


    public LocalDateTime invitationLimitDate() {
        return invitationLimitDate;
    }

    public void addExpense(String supplyName, Double supplyPrice) {
        if (supplyPrice < 0) {
            throw new RuntimeException(ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE);
        }
        expenses.put(supplyName, supplyPrice);
    }

    @Override
    public void confirmAssistance(String anEmail, LocalDateTime confirmationDate) {
        validateThatTheConfirmationDateIsValidWithTheInvitationLimitDate(confirmationDate);
        super.confirmAssistance(anEmail, confirmationDate);
    }

    private void validateThatTheConfirmationDateIsValidWithTheInvitationLimitDate(LocalDateTime confirmationDate) {
        if (confirmationDate.isAfter(this.invitationLimitDate)) {
            throw new EventException(ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT);
        }
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

}
