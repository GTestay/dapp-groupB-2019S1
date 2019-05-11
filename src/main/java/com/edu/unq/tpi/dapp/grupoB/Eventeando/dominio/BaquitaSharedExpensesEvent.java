package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.javamoney.moneta.Money;

public class BaquitaSharedExpensesEvent extends Event {


    public Money costPerAssitance() {
        return Money.of(expensesTotalCost() / numberOfAssistantsWithOrganizer(), "ARS");
    }

    private int numberOfAssistantsWithOrganizer() {
        return guests.size() + 1;
    }

}
