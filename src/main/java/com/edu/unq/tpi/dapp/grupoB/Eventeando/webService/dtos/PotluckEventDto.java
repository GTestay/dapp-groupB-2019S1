package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class PotluckEventDto extends EventDto {

    @JsonCreator
    public PotluckEventDto(String organizerEmail, String description, List<String> guestsEmails, List<Long> expensesIds) {
        super(organizerEmail, description, guestsEmails, expensesIds);
    }


    public String getType() {
        return "PotluckEvent";
    }
}
