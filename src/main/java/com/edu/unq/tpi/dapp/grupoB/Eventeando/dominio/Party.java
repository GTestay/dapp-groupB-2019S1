package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;

import javax.persistence.Entity;
import java.time.LocalDateTime;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT;

@Entity
public class Party extends Event {

    protected LocalDateTime invitationLimitDate;
    protected Double pricePerAssistant;


    public LocalDateTime invitationLimitDate() {
        return invitationLimitDate;
    }

    public void addExpense(Expense expense) {

        expenses.add(expense);
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
