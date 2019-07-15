package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DepositByCreditCardInformation extends TransactionInformation {
    @JsonProperty
    private YearMonth dueDate;

    @JsonProperty
    private String cardNumber;

    @JsonCreator
    public DepositByCreditCardInformation(double amount, String dueDate, String cardNumber) {
        super(amount);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMuu");
        this.dueDate = YearMonth.parse(dueDate, formatter);
        this.cardNumber = cardNumber;
    }

    public YearMonth dueDate() {
        return dueDate;
    }

    public String cardNumber() {
        return cardNumber;
    }
}
