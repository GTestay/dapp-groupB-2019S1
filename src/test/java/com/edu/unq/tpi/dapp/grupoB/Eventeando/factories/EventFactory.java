package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Party;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventFactory {

    private UserFactory userFactory;

    public EventFactory(UserFactory userFactory) {

        this.userFactory = userFactory;
    }

    private Party createParty(List<User> guests, List<Expense> expenses, Double pricePerAssistant) {
        return Event.createParty(userFactory.user(), description(), guests, expenses, anInvitationLimitDate(), pricePerAssistant);
    }

    public List<Expense> expenses() {
        return Arrays.asList(coca(), sanguchitos());
    }

    public LocalDateTime anInvitationLimitDate() {
        return LocalDateTime.now();
    }

    private Double ticketPrice() {
        return 0.0;
    }

    private String description() {
        return "An event description";
    }

    public Party partyWithGuests(List<User> guests) {
        return createParty(guests, expenses(), ticketPrice());
    }

    public Party partyWithGuestsExpensesAndAPricePerAssistant(List<User> guests, Double pricePerAssistant, List<Expense> expenses) {
        return createParty(guests, expenses, pricePerAssistant);
    }

    public LocalDateTime invalidConfirmationDate() {
        return this.anInvitationLimitDate().plusDays(1);
    }


    public LocalDateTime confirmationDate() {
        return this.anInvitationLimitDate().minusDays(1);
    }

    public List<Expense> noExpenses() {
        return new ArrayList<>();
    }

    public Expense sanguchitos() {
        return new Expense("Sanguches de Miga x 24", 100.00);
    }

    public Expense coca() {
        return new Expense("Coca 3L", 100.00);
    }
}
