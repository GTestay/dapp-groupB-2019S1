package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import java.util.List;
import java.util.Map;

public class BaquitaEvent extends Event {

    public static BaquitaEvent create(User organizer, String description, Map<String, Double> expenses, List<User> assistants) {
        return (BaquitaEvent) validateInstance(new BaquitaEvent(),organizer,description,expenses,assistants);
    }


    public Double costPerAssitance() {
        return expensesTotalCost() / numberOfAssistantsWithOrganizer();
    }

    private int numberOfAssistantsWithOrganizer() {
        return guests.size() + 1;
    }
}
