package com.edu.unq.tpi.dapp.grupoB.Eventeando.validator;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventException;

import java.util.List;

public class EventValidator extends Validator {
    public static final String EVENT_IS_INVALID_WITHOUT_ORGANIZER = "Error, the event needs an organizer!";
    public static final String EVENT_IS_INVALID_WITHOUT_GUESTS = "Error, the event needs guests!";

    public static final String ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT = "Error, the confirmation date es after the invitation limit date";

    public static final String ERROR_THE_USER_WAS_NOT_INVITED = "error, the user was not invited";
    public static final String ERROR_EXPENSE_IS_NOT_IN_THE_LIST = "Error, the expense is not the list";
    public static final String ERROR_EXPENSE_IS_ALREADY_COVERED = "Error, the expense is already covered by other user";
    public static final String ERROR_CAN_NOT_ADD_MORE_MONEY_THE_CROWDFUNDING_EVENT_IS_FUNDED = "Error, the crowdfunding event has the total amount of money that was required";
    public static final String ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT = "Error, can not add more funds than is required in the crowdfunding event.";
    public static final String ERROR_THE_AMOUNT_IS_INVALID = "Error, the amount given is negative or zero";

    public User validateOrganizer(User organizer) {
        return validateNullityOf(organizer, new EventException(EVENT_IS_INVALID_WITHOUT_ORGANIZER));
    }

    public List<User> validateGuests(List<User> guests) {
        return validateEmptinessOf(guests, new EventException(EVENT_IS_INVALID_WITHOUT_GUESTS));
    }

}
