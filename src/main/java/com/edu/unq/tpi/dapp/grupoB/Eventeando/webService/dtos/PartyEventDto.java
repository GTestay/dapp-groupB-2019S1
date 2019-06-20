package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class PartyEventDto extends EventDto {

    @JsonProperty
    private LocalDateTime invitationLimitDate;

    @JsonCreator
    public PartyEventDto(String organizerEmail, String description, List<String> guestsEmails, LocalDateTime invitationLimitDate, List<Long> expensesIds) {
        super(organizerEmail, description, guestsEmails, expensesIds);
        this.invitationLimitDate = invitationLimitDate;
    }

    public LocalDateTime getInvitationLimitDate() {
        return invitationLimitDate;
    }

    public void setInvitationLimitDate(LocalDateTime invitationLimitDate) {
        this.invitationLimitDate = invitationLimitDate;
    }

    public String getType() {
        return "Party";
    }
}
