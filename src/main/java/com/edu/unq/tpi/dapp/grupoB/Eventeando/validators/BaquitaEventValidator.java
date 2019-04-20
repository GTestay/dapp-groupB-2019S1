package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.BaquitaEventException;

import java.util.List;

public class BaquitaEventValidator {
    public static final String BAQUITA_EVENT_IS_INVALID_WITHOUT_ORGANIZER = "Error, the event needs an organizer!";
    public static final String BAQUITA_EVENT_IS_INVALID_WITHOUT_ASSISTANTS = "Error, the event needs assistants!";

    public User validateOrganizer(User organizer) {
        validateNullityOf(organizer, new BaquitaEventException(BAQUITA_EVENT_IS_INVALID_WITHOUT_ORGANIZER));
        return organizer;
    }

    public List<User> validateAssistants(List<User> assistants) {
        if (assistants == null || assistants.isEmpty()) {
            throw new BaquitaEventException(BAQUITA_EVENT_IS_INVALID_WITHOUT_ASSISTANTS);
        }
        return assistants;
    }

    private void validateNullityOf(Object field, BaquitaEventException exception) {
        if (field == null) {
            throw exception;
        }
    }

}
