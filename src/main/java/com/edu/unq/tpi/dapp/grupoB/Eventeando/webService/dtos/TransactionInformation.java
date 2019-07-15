package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionInformation {
    @JsonProperty
    protected double amount;

    @JsonCreator
    public TransactionInformation(double amount) {
        this.amount = amount;
    }

    public double amount() {
        return amount;
    }
}
