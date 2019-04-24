package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.DoubleStream;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE;
import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_USER_WAS_NOT_INVITED;

public class Party extends Event {

    private LocalDateTime invitationLimitDate;
    private Double pricePerAssistant;
    private List<String> guestConfirmations;

    public static Party create(User organizer, List<User> assistants, LocalDateTime invitationLimitDate, Double pricePerAssistant) {
        EventValidator eventValidator = new EventValidator();
        Party instance = new Party();
        instance.invitationLimitDate = invitationLimitDate;
        instance.pricePerAssistant = pricePerAssistant;
        instance.organizer = organizer;
        instance.guests = eventValidator.validateAssistants(assistants);
        instance.expenses = new HashMap<>();
        instance.guestConfirmations = new ArrayList<>();
        return instance;
    }


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
}
