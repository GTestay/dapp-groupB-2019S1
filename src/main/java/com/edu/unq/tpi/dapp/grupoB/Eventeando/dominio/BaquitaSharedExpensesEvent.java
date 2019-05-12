package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import javax.persistence.Entity;

@Entity
public class BaquitaSharedExpensesEvent extends Event {


    public Double costPerAssitance() {
        return expensesTotalCost() / numberOfAssistantsWithOrganizer();
    }

    private int numberOfAssistantsWithOrganizer() {
        return guests.size() + 1;
    }

}
