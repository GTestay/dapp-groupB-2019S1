package com.edu.unq.tpi.dapp.grupoB.Eventeando.factory;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class EventFactory {

    public Party partyWithGuests(List<User> guests, User organizer, List<Expense> expenses) {
        return partyWithGuestsExpensesAndAPricePerAssistant(guests, expenses, organizer);
    }

    public PotluckEvent potluckWithGuests(List<User> guests, User organizer, List<Expense> expenses) {
        return PotluckEvent.create(organizer, description(), guests, expenses);
    }

    public BaquitaSharedExpensesEvent baquitaSharedExpenses(User organizer, List<User> guests, List<Expense> expenses) {
        return BaquitaSharedExpensesEvent.create(organizer, description(), guests, expenses);
    }

    public BaquitaCrowdFundingEvent baquitaCrowfunding(User organizer, List<User> guests, List<Expense> expenses) {
        return BaquitaCrowdFundingEvent.create(organizer, description(), guests, expenses);
    }

    private Party createParty(List<User> guests, List<Expense> expenses, User organizer) {
        return Party.create(organizer, description(), guests, expenses, anInvitationLimitDate());
    }

    public List<Expense> expenses() {
        return Arrays.asList(coca(), sanguchitos());
    }

    public LocalDateTime anInvitationLimitDate() {
        return LocalDateTime.now();
    }

    public String description() {
        return "An event description";
    }

    public Party partyWithGuestsExpensesAndAPricePerAssistant(List<User> guests, List<Expense> expenses, User organizer) {
        return createParty(guests, expenses, organizer);
    }

    public LocalDateTime invalidConfirmationDate() {
        return this.anInvitationLimitDate().plusDays(1);
    }

    public LocalDateTime confirmationDate() {
        return this.anInvitationLimitDate().minusDays(1);
    }


    public Expense sanguchitos() {
        return Expense.create("Sanguches de Miga x 24", 100.00);
    }

    public Expense coca() {
        return Expense.create("Coca 3L", 100.00);
    }
}
