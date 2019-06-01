package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("BaquitaSharedExpenses")
public class BaquitaSharedExpensesEvent extends Event {


    @JsonCreator
    public static BaquitaSharedExpensesEvent create
    (
        @JsonProperty("organizer") User organizer,
        @JsonProperty("description") String description,
        @JsonProperty("guests") List<User> guests,
        @JsonProperty("expenses") List<Expense> expenses
    )
    {
        return validateEvent(new BaquitaSharedExpensesEvent(), organizer, description, expenses, guests);
    }

    public Double costPerAssitance() {
        return expensesTotalCost() / numberOfAssistantsWithOrganizer();
    }

    private int numberOfAssistantsWithOrganizer() {
        return guests.size() + 1;
    }

}
