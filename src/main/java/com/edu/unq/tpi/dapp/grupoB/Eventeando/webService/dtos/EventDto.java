package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PartyEventDto.class, name = "Party"),
        @JsonSubTypes.Type(value = BaquitaSharedExpensesEventDto.class, name = "BaquitaSharedExpensesEvent"),
        @JsonSubTypes.Type(value = BaquitaCrowdFundingEventDto.class, name = "BaquitaCrowdFundingEvent"),
        @JsonSubTypes.Type(value = PotluckEventDto.class, name = "PotluckEvent"),
})
public abstract class EventDto {

    protected String organizerEmail;
    protected String description;
    protected List<String> guestsEmails;
    protected List<Long> expensesIds;

    public EventDto(String organizerEmail, String description, List<String> guestsEmails, List<Long> expensesIds) {
        this.organizerEmail = organizerEmail;
        this.description = description;
        this.guestsEmails = guestsEmails;
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

    public abstract String getType();

}
