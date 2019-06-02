package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("BaquitaSharedExpenses")
public class BaquitaSharedExpensesEvent extends Event {


    @JsonCreator
    public static BaquitaSharedExpensesEvent create(User organizer, String description, List<User> guests, List<Expense> expenses) {
        return validateEvent(new BaquitaSharedExpensesEvent(), organizer, description, expenses, guests);
    }

    public Double costPerAssitance() {
        return expensesTotalCost() / numberOfAssistantsWithOrganizer();
    }

    private int numberOfAssistantsWithOrganizer() {
        return guests.size() + 1;
    }

}
