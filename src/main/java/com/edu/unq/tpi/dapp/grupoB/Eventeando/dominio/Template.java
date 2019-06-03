package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Template {

    public static final String INVALID_TEMPLATE_CREATION = "Invalid Template creation";

    private ArrayList<User> usesForUsers;
    private String description;
    private List<Expense> expenses;

    private Template(String description, List<Expense> expenses) {
        this.usesForUsers = new ArrayList<>();
        this.description = description;
        this.expenses = expenses;
    }

    public static Template create(String description, List<Expense> expenses) {
        validateTemplateCreation(description, expenses);

        return new Template(description, expenses);
    }

    private static void validateTemplateCreation(String description, List<Expense> expenses) {
        Validator validator = new Validator();
        validator.validateEmptinessOf(description, new RuntimeException(INVALID_TEMPLATE_CREATION));
        validator.validateEmptinessOf(expenses, new RuntimeException(INVALID_TEMPLATE_CREATION));
    }

    public String description() {
        return description;
    }

    public List<Expense> expenses() {
        return expenses;
    }

    public Party generateNewPartyEventWith(User user, List<User> guests, LocalDateTime anInvitationLimitDate) {
        Party party = Party.create(user, description, guests, expenses, anInvitationLimitDate);
        this.usesForUsers.add(user);
        return party;
    }

    public Boolean hasUser(User user) {
        return this.usesForUsers.stream().anyMatch(organizer -> organizer.hasThisEmail(user.email()));
    }

    public PotluckEvent generateNewPotluckEventWith(User user, List<User> guests) {
        PotluckEvent potluckEvent = PotluckEvent.create(user, description, guests, expenses);
        this.usesForUsers.add(user);
        return potluckEvent;
    }

    public BaquitaSharedExpensesEvent generateNewBaquitaSharedExpensesEventWith(User user, List<User> guests) {
        BaquitaSharedExpensesEvent potluckEvent = BaquitaSharedExpensesEvent.create(user, description, guests, expenses);
        this.usesForUsers.add(user);
        return potluckEvent;
    }

    public BaquitaCrowdFundingEvent generateNewBaquitaCrowdFundingEventWith(User user, List<User> guests) {
        BaquitaCrowdFundingEvent potluckEvent = BaquitaCrowdFundingEvent.create(user, description, guests, expenses);
        this.usesForUsers.add(user);
        return potluckEvent;
    }
}
