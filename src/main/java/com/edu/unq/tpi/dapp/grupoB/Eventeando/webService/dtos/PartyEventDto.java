package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartyEventDto {

    private String type;
    private String organizerEmail;
    private String description;
    private List<String> guestsEmails;
    private LocalDateTime invitationLimitDate;
    private List<Long> expensesIds;

    @JsonCreator
    public PartyEventDto(String type, String organizerEmail, String description, List<String> guestsEmails, LocalDateTime invitationLimitDate, List<Long> expensesIds) {
        this.type = type;
        this.organizerEmail = organizerEmail;
        this.description = description;
        this.guestsEmails = guestsEmails;
        this.invitationLimitDate = invitationLimitDate;
        this.expensesIds = expensesIds;
    }


    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public List<String> getGuestsEmails() {
        return guestsEmails;
    }

    public void setGuestsEmails(ArrayList<String> guestsEmails) {
        this.guestsEmails = guestsEmails;
    }

    public LocalDateTime getInvitationLimitDate() {
        return invitationLimitDate;
    }

    public void setInvitationLimitDate(LocalDateTime invitationLimitDate) {
        this.invitationLimitDate = invitationLimitDate;
    }

    public List<Long> getExpensesIds() {
        return expensesIds;
    }

    public void setExpensesIds(ArrayList<Long> expensesIds) {
        this.expensesIds = expensesIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
