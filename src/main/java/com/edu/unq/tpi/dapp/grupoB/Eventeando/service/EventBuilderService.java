package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.EventData;

import java.time.LocalDateTime;
import java.util.List;

public class EventBuilderService {
    private final User organizer;
    private final List<User> guests;
    private final List<Expense> expenses;
    private final String description;

    public EventBuilderService(EventData eventData, EventService eventService) {
        organizer = eventService.findUserByEmail(eventData.getOrganizerEmail());
        guests = eventService.obtainUsersFromEmails(eventData.getGuestsEmails());
        expenses = eventService.getExpenses(eventData.getExpensesIds());
        description = eventData.getDescription();
    }

    public Party buildParty(LocalDateTime invitationLimitDate) {
        return Party.create(organizer, description, guests, expenses, invitationLimitDate);
    }

    public PotluckEvent buildPotluckEvent() {
        return PotluckEvent.create(organizer, description, guests, expenses);
    }

    public BaquitaSharedExpensesEvent buildBaquitaSharedExpensesEvent() {
        return BaquitaSharedExpensesEvent.create(organizer, description, guests, expenses);

    }

    public BaquitaCrowdFundingEvent buildBaquitaCrowdFundingEventEvent() {
        return BaquitaCrowdFundingEvent.create(organizer, description, guests, expenses);
    }
}
