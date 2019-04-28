package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Party;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class EventFactory {

    private UserFactory userFactory;

    public EventFactory(UserFactory userFactory) {

        this.userFactory = userFactory;
    }

    private Party createParty(List<User> guests) {
        return Event.createParty(userFactory.user(), description(), guests, expenses(), anInvitationLimitDate(), ticketPrice());
    }

    private HashMap<String, Double> expenses() {
        HashMap<String, Double> expenses = new HashMap<>();
        expenses.put("Coca 3L", 100.0);
        expenses.put("Sanguchitos x 24 ", 100.0);
        return expenses;
    }

    private LocalDateTime anInvitationLimitDate() {
        return LocalDateTime.now();
    }

    private Double ticketPrice() {
        return 0.0;
    }

    private String description() {
        return "An event description";
    }

    public Party partyWithGuests(List<User> guests) {
        return createParty(guests);
    }
}
