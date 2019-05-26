package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventFactory {

    private Party createParty(List<User> guests, List<Expense> expenses, Double pricePerAssistant, User organizer) {
        return Party.create(organizer, description(), guests, expenses, anInvitationLimitDate(), pricePerAssistant);
    }

    public PotluckEvent potluckWithGuests(List<User> guests, User organizer) {
        return PotluckEvent.create(organizer, description(), guests, expenses());
    }

    public BaquitaSharedExpensesEvent baquitaSharedExpenses(User organizer, List<User> guests) {
        return BaquitaSharedExpensesEvent.create(organizer, description(), guests, expenses());
    }

    public BaquitaCrowdFundingEvent baquitaCrowfunding(User organizer, List<User> guests) {
        return BaquitaCrowdFundingEvent.create(organizer, description(), guests, expenses());
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

    public Party partyWithGuests(List<User> guests, User organizer) {
        return partyWithGuestsExpensesAndAPricePerAssistant(guests, ticketPrice(), expenses(), organizer);
    }

    public Party partyWithGuestsExpensesAndAPricePerAssistant(List<User> guests, Double pricePerAssistant, List<Expense> expenses, User organizer) {
        return createParty(guests, expenses, pricePerAssistant, organizer);
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
        return Expense.create("Sanguches de Miga x 24", 100.00);
    }

    public Expense coca() {
        return Expense.create("Coca 3L", 100.00);
    }
}
