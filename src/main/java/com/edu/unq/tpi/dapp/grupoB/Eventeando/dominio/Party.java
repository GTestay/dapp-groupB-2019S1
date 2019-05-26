package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT;

@Entity
@DiscriminatorValue("Party")
public class Party extends Event {

    @JsonProperty
    protected LocalDateTime invitationLimitDate;
    @JsonProperty
    protected Double pricePerAssistant;

    @JsonCreator
    public static Party create(User organizer, String description, List<User> guests, List<Expense> expenses, LocalDateTime invitationLimitDate, Double pricePerAssistant) {
        EventValidator eventValidator = new EventValidator();
        Party instance = validateEvent(new Party(), organizer, description, expenses, guests);
        instance.invitationLimitDate = invitationLimitDate;
        instance.pricePerAssistant = eventValidator.validatePricePerAssistant(pricePerAssistant);
        return instance;
    }


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
