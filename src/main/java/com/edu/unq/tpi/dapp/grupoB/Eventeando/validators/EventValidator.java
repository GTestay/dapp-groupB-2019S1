package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;

import java.util.List;

public class EventValidator extends Validator {
    public static final String EVENT_IS_INVALID_WITHOUT_ORGANIZER = "Error, the event needs an organizer!";
    public static final String EVENT_IS_INVALID_WITHOUT_ASSISTANTS = "Error, the event needs assistants!";

    public User validateOrganizer(User organizer) {
        return validateNullityOf(organizer, new EventException(EVENT_IS_INVALID_WITHOUT_ORGANIZER));
    }

    public List<User> validateAssistants(List<User> assistants) {
        return validateEmptinessOf(assistants, new EventException(EVENT_IS_INVALID_WITHOUT_ASSISTANTS));
    }
}
