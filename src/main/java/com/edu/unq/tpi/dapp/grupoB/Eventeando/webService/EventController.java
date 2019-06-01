package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Party;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.EventService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.PartyEventDto;
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
    public Party createParty(@RequestBody PartyEventDto partyEventDto) {

        return eventService.createParty(
                partyEventDto.getOrganizerEmail(),
                partyEventDto.getDescription(), partyEventDto.getGuestsEmails(),
                partyEventDto.getInvitationLimitDate(),
                partyEventDto.getExpensesIds());
    }


}
