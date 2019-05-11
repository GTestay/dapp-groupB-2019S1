package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator.ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE;

public class Expense {
    private final String name;
    private final Double cost;

    public Expense(String name, Double cost) {
        if (cost < 0) {
            throw new RuntimeException(ERROR_CAN_NOT_ADD_EXPENSE_WHOSE_PRICE_IS_NEGATIVE);
        }
        this.name = name;
        this.cost = cost;
    }

    public String name() {
        return name;
    }

    public Double cost() {
        return cost;
    }
}
