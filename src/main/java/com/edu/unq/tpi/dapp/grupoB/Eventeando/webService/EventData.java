package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import java.util.List;

public class EventData {
    private final String organizerEmail;
    private final String description;
    private final List<String> guestsEmails;
    private final List<Long> expensesIds;

    public EventData(String organizerEmail, String description, List<String> guestsEmails, List<Long> expensesIds) {
        this.organizerEmail = organizerEmail;
        this.description = description;
        this.guestsEmails = guestsEmails;
        this.expensesIds = expensesIds;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGuestsEmails() {
        return guestsEmails;
    }

    public List<Long> getExpensesIds() {
        return expensesIds;
    }
}
