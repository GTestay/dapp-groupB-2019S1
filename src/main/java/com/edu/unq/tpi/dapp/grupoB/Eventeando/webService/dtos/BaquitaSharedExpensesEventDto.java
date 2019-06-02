package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import java.util.List;

public class BaquitaSharedExpensesEventDto extends EventDto {

    public BaquitaSharedExpensesEventDto(String organizerEmail, String description, List<String> guestsEmails, List<Long> expensesIds) {
        super(organizerEmail, description, guestsEmails, expensesIds);
    }

    @Override
    public String getType() {
        return "BaquitaSharedExpensesEvent";
    }
}
