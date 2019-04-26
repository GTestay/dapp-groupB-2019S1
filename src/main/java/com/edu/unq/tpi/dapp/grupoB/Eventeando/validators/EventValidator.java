package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;

import java.util.List;

public class EventValidator extends Validator {
    public static final String EVENT_IS_INVALID_WITHOUT_ORGANIZER = "Error, the event needs an organizer!";
    public static final String EVENT_IS_INVALID_WITHOUT_GUESTS = "Error, the event needs guests!";

    public static final String ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE = "error, can't not add an expense whose price is negative";
    public static final String ERROR_THE_USER_WAS_NOT_INVITED = "error, the user was not invited";
    public static final String ERROR_EXPENSE_IS_NOT_IN_THE_LIST = "Error, the expense is not the list";
    public static final String ERROR_EXPENSE_IS_ALREADY_COVERED = "Error, the expense is already covered by other user";
    public static final String EVENT_TICKET_PRICE_MUST_NOT_BE_NEGATIVE = "Error, the ticket price can not be negative.";

    public User validateOrganizer(User organizer) {
        return validateNullityOf(organizer, new EventException(EVENT_IS_INVALID_WITHOUT_ORGANIZER));
    }

    public List<User> validateAssistants(List<User> assistants) {
        return validateEmptinessOf(assistants, new EventException(EVENT_IS_INVALID_WITHOUT_GUESTS));
    }

    public Double validatePricePerAssistant(Double pricePerAssistant) {
        return validateNegativiyOf(pricePerAssistant, new EventException(EVENT_TICKET_PRICE_MUST_NOT_BE_NEGATIVE));

    }
}
