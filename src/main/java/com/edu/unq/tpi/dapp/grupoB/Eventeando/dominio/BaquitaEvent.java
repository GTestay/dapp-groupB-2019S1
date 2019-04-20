package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.BaquitaEventValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class BaquitaEvent {
    private User organizer;
    private String description;
    private Map<String, Double> expenses;
    private List<User> assistants;


    public static BaquitaEvent create(User organizer, String description, Map<String, Double> expenses, List<User> assistants) {
        BaquitaEventValidator baquitaEventValidator = new BaquitaEventValidator();
        BaquitaEvent instance = new BaquitaEvent();

        instance.organizer = baquitaEventValidator.validateOrganizer(organizer);
        instance.description = description;
        instance.expenses = expenses;
        instance.assistants = baquitaEventValidator.validateAssistants(assistants);

        return instance;
    }

    public User organizer() {
        return organizer;
    }

    public Double expensesTotalCost() {
        return allExpensesCost().sum();
    }

    private DoubleStream allExpensesCost() {
        return expenses.values().stream().mapToDouble(d -> d);
    }

    public String description() {
        return description;
    }


    public Double costPerAssitance() {
        return expensesTotalCost() / numberOfAssistantsWithOrganizer();
    }

    private int numberOfAssistantsWithOrganizer() {
        return assistants.size() + 1;
    }
}
