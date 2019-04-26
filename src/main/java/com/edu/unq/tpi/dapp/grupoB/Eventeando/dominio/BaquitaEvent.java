package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

public class BaquitaEvent extends Event {


    public Double costPerAssitance() {
        return expensesTotalCost() / numberOfAssistantsWithOrganizer();
    }

    private int numberOfAssistantsWithOrganizer() {
        return guests.size() + 1;
    }
}
