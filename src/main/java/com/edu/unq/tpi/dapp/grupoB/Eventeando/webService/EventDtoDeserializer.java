package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.EventService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.EventDto;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.PartyEventDto;

class EventDtoDeserializer {

    public static final String PARTY = "Party";
    public static final String POTLUCK_EVENT = "PotluckEvent";
    public static final String BAQUITA_SHARED_EXPENSES_EVENT = "BaquitaSharedExpensesEvent";
    public static final String BAQUITA_CROWD_FUNDING_EVENT = "BaquitaCrowdFundingEvent";

    private EventController eventController;
    private EventDto eventDto;

    private EventDtoDeserializer(EventController eventController, EventService eventService, EventDto eventDto) {
        this.eventController = eventController;
        this.eventDto = eventDto;
    }

    public static EventDtoDeserializer createEventDtoDeserializer(EventController eventController, EventService eventService, EventDto eventDto) {
        return new EventDtoDeserializer(eventController, eventService, eventDto);
    }

    public Event invoke() {
        return createEventFromDto(eventDto);
    }

    private Event createEventFromDto(EventDto eventDto) {
        EventData eventData = new EventData(eventDto.getOrganizerEmail(), eventDto.getDescription(), eventDto.getGuestsEmails(), eventDto.getExpensesIds());
        switch (eventDto.getType()) {
            case PARTY:
                return eventController.createParty(eventData, ((PartyEventDto) eventDto).getInvitationLimitDate());
            case POTLUCK_EVENT:
                return eventController.createPotluckEvent(eventData);
            case BAQUITA_SHARED_EXPENSES_EVENT:
                return eventController.createBaquitaSharedExpensesEvent(eventData);
            case BAQUITA_CROWD_FUNDING_EVENT:
                return eventController.createBaquitaCrowdFundingEvent(eventData);
            default:
                throw new IllegalStateException("Invalid type: " + eventDto.getType());
        }
    }
}
