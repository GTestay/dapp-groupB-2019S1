package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.EventService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Transactional
public class EventController {

    private static final String baseUrl = "/events";

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(baseUrl)
    public List<Event> events() {
        return eventService.allEvents();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/popularEvents")
    public List<Event> popularEvents() {
        return eventService.popularEvents();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(baseUrl + "/{eventId}/score")
    public void scoreAnEvent(@PathVariable Long eventId, @RequestBody DtoScore score) {
        eventService.scoreAnEvent(eventId, score.userId(), score.rank());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(baseUrl + "/{eventId}/score")
    public Integer eventTotalScore(@PathVariable Long eventId) {
        return eventService.eventScore(eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(baseUrl + "/{id}")
    public List<Event> eventsWithOrganizer(@PathVariable(value = "id") Long organizerId) {
        return eventService.allEventsOf(organizerId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(baseUrl)
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
