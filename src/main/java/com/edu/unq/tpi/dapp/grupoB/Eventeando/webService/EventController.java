package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.EventService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(baseUrl + "/{id}")
    @ResponseBody
    public List<Event> eventsWithOrganizer(@PathVariable(value = "id") Long organizerId) {
        return eventService.allEventsOf(organizerId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(baseUrl)
    @ResponseBody
    public Event createEvent(@RequestBody EventDto eventDto) {
        return deserializeEventDto(eventDto);
    }

    public Event deserializeEventDto(@RequestBody EventDto eventDto) {
        return EventDtoDeserializer.createEventDtoDeserializer(this, eventService, eventDto).invoke();
    }

    BaquitaCrowdFundingEvent createBaquitaCrowdFundingEvent(EventData eventData) {
        return eventService.createBaquitaCrowdFundingEvent(eventData);
    }

    BaquitaSharedExpensesEvent createBaquitaSharedExpensesEvent(EventData eventData) {
        return eventService.createBaquitaSharedExpensesEvent(eventData);
    }

    PotluckEvent createPotluckEvent(EventData eventData) {
        return eventService.createPotluckEvent(eventData);
    }

    Party createParty(EventData eventData, LocalDateTime invitationLimitDate) {
        return eventService.createParty(eventData, invitationLimitDate);
    }


}
