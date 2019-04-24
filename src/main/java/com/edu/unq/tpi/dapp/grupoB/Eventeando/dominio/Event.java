package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_THE_USER_WAS_NOT_INVITED;

public abstract class Event {

    protected User organizer;
    protected String description;
    protected Map<String, Double> expenses;
    protected List<User> guests;

    protected static Event validateInstance(Event event, User organizer, String description, Map<String, Double> expenses, List<User> guests) {

        EventValidator eventValidator = new EventValidator();
        event.organizer = eventValidator.validateOrganizer(organizer);
        event.description = description;
        event.expenses = expenses;
        event.guests = eventValidator.validateAssistants(guests);

        return event;
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
        return expenses.values().stream().mapToDouble(d -> d);
    }

    protected void validateThatTheUserWasInvited(String anEmail) {
        if (!isInvited(anEmail)) {
            throw new EventException(ERROR_THE_USER_WAS_NOT_INVITED);
        }
    }

    protected boolean isInvited(String anEmail) {
        return guests.stream().anyMatch(user -> user.hasEmail(anEmail));
    }
}
