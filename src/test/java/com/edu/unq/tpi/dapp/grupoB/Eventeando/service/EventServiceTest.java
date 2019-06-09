package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EventServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;
    @Autowired
    private EventService eventService;
    private UserFactory userFactory;
    private EventFactory eventFactory;
    private User organizer;


    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
        organizer = userFactory.user();
    }

    @Test
    public void noneEventIsRetrieved() {
        List<Event> events = eventService.allEvents();
        assertThat(events).isEmpty();
    }

    @Test
    public void anEventIsRetrieved() {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()), organizer);
        eventDao.save(anEvent);

        List<Event> events = eventService.allEvents();

        assertThat(events).containsOnlyOnce(anEvent);
    }


    @Test
    public void givenAnUserIdAndUserHasNoEvents() {
        userDao.save(organizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).isEmpty();
    }

    @Test
    public void givenAnUserIdAllEventOfTheUserAreRetrieved() {
        organizer = userFactory.user();

        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()), organizer);
        eventDao.save(anEvent);

        Event anEventWithTheUserThatIsNotTheOrganizer = eventFactory.partyWithGuests(Collections.singletonList(organizer), userFactory.user());
        eventDao.save(anEventWithTheUserThatIsNotTheOrganizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).containsOnlyOnce(anEvent);
    }

    @Test
    public void givenAnUserIdAllEventOfTheUserAreRetrievedThatAreOnlyAnOrganizer() {
        organizer = userFactory.user();
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()), organizer);
        eventDao.save(anEvent);

        Event anEventWithTheUserThatIsNotTheOrganizer = eventFactory.partyWithGuests(Collections.singletonList(organizer), userFactory.user());
        eventDao.save(anEventWithTheUserThatIsNotTheOrganizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).containsOnlyOnce(anEvent);
    }

    @Test
    public void whenTheEventIsCreatedItSendsMailsToTheGuests() {
        organizer = userFactory.user();
        User guest = userFactory.user();
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(guest), organizer);

        eventService.sendInvitations(anEvent);

        assertThat(guest.invitations()).isNotEmpty();
    }


}