package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.EventService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    private static final String baseUrl = "/events";

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(baseUrl)
    @ResponseBody
    public List<Event> events() {
        return eventService.allEvents();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(baseUrl)
    @ResponseBody
    public Event createEvent(@RequestBody EventDto eventDto) {
        switch (eventDto.getType()) {
            case "Party":
                return createParty((PartyEventDto) eventDto);
            case "PotluckEvent":
                return createPotluckEvent((PotluckEventDto) eventDto);
            case "BaquitaSharedExpensesEvent":
                return createBaquitaSharedExpensesEvent((BaquitaSharedExpensesEventDto) eventDto);
            case "BaquitaCrowdFundingEvent":
                return createBaquitaCrowdFundingEvent((BaquitaCrowdFundingEventDto) eventDto);
            default:
                throw new IllegalStateException("Unexpected value: " + eventDto.getType());
        }

    }

    private BaquitaCrowdFundingEvent createBaquitaCrowdFundingEvent(BaquitaCrowdFundingEventDto baquitaCrowdFundingEventDto) {
        return eventService.createBaquitaCrowdFundingEvent(
                baquitaCrowdFundingEventDto.getOrganizerEmail(),
                baquitaCrowdFundingEventDto.getDescription(),
                baquitaCrowdFundingEventDto.getGuestsEmails(),
                baquitaCrowdFundingEventDto.getExpensesIds());
    }

    private BaquitaSharedExpensesEvent createBaquitaSharedExpensesEvent(BaquitaSharedExpensesEventDto baquitaSharedExpensesEventDto) {
        return eventService.createBaquitaSharedExpensesEvent(
                baquitaSharedExpensesEventDto.getOrganizerEmail(),
                baquitaSharedExpensesEventDto.getDescription(),
                baquitaSharedExpensesEventDto.getGuestsEmails(),
                baquitaSharedExpensesEventDto.getExpensesIds());
    }

    private PotluckEvent createPotluckEvent(PotluckEventDto potluckEventDto) {
        return eventService.createPotluckEvent(
                potluckEventDto.getOrganizerEmail(),
                potluckEventDto.getDescription(),
                potluckEventDto.getGuestsEmails(),
                potluckEventDto.getExpensesIds());
    }

    private Party createParty(PartyEventDto partyEventDto) {
        return eventService.createParty(
                partyEventDto.getOrganizerEmail(),
                partyEventDto.getDescription(),
                partyEventDto.getGuestsEmails(),
                partyEventDto.getInvitationLimitDate(),
                partyEventDto.getExpensesIds());
    }


}
