package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_USER_WAS_NOT_INVITED;

public abstract class Event {

    protected User organizer;
    protected String description;
    protected Map<String, Double> expenses;
    protected List<User> guests;

    protected static <EventType extends Event> EventType validateEvent(EventType newEvent, User organizer, String description, Map<String, Double> expenses, List<User> guests) {
        EventValidator eventValidator = new EventValidator();
        newEvent.organizer = eventValidator.validateOrganizer(organizer);
        newEvent.description = description;
        newEvent.expenses = expenses;
        newEvent.guests = eventValidator.validateGuests(guests);
        return newEvent;
    }

    public static Party createParty(User organizer, String description, List<User> guests, HashMap<String, Double> expenses, LocalDateTime invitationLimitDate, Double pricePerAssistant) {
        EventValidator eventValidator = new EventValidator();
        Party instance = validateEvent(new Party(), organizer, description, expenses, guests);
        instance.guestConfirmations = new ArrayList<>();
        instance.invitationLimitDate = invitationLimitDate;
        instance.pricePerAssistant = eventValidator.validatePricePerAssistant(pricePerAssistant);
        return instance;
    }

    public static PotluckEvent createPotluck(User organizer, String description, List<User> guests, Map<String, Double> expenses) {
        return validateEvent(new PotluckEvent(), organizer, description, expenses, guests);
    }

    public static BaquitaSharedExpensesEvent createBaquita(User organizer, String description, List<User> guests, Map<String, Double> expenses) {
        return validateEvent(new BaquitaSharedExpensesEvent(), organizer, description, expenses, guests);
    }

    public static BaquitaCrowdFundingEvent createBaquitaCrowdfunding(User organizer, String description, List<User> guests, Map<String, Double> expenses) {
        return validateEvent(new BaquitaCrowdFundingEvent(), organizer, description, expenses, guests);
    }

    public User organizer() {
        return organizer;
    }

    public String description() {
        return description;
    }

    public Map<String, Double> expenses() {
        return expenses;
    }

    public List<User> guests() {
        return guests;
    }

    public Double totalCost() {
        return expensesTotalCost();
    }

    public Double expensesTotalCost() {
        return allExpensesCost().sum();
    }

    private DoubleStream allExpensesCost() {
        return expenses.values().stream().mapToDouble(cost -> cost);
    }

    protected void validateThatTheUserWasInvited(String anEmail) {
        if (!isInvited(anEmail)) {
            throw new EventException(ERROR_THE_USER_WAS_NOT_INVITED);
        }
    }

    protected boolean isInvited(String anEmail) {
        return guests.stream().anyMatch(user -> user.hasThisEmail(anEmail));
    }

    protected void throwEventException(String errorMessage) {
        throw new EventException(errorMessage);
    }
}
