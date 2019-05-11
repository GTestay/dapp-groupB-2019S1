package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

public class Expense {
    private final String name;
    private final Double cost;

    private Expense(String name, Double cost) {

        this.name = name;
        this.cost = cost;
    }

    public static Expense create(String name, Double cost) {
        ExpenseValidator validator = new ExpenseValidator();
        validator.validateCost(cost);
        validator.validateName(name);

        return new Expense(name, cost);
    }

    public String name() {
        return name;
    }

    public Double cost() {
        return cost;
    }
}
