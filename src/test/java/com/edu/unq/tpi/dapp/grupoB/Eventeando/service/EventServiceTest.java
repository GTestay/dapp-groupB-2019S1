package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.MailSenderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

    @MockBean
    private MailSenderService mailSenderService;

    @MockBean
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
    }

    @Test
    public void noneEventIsRetrieved() {
        List<Event> events = eventService.allEvents();
        assertThat(events).isEmpty();
    }

    @Test
    public void anEventIsRetrieved() {
        organizer = userFactory.user();
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()), organizer);

        given(this.eventDao.findAll()).willReturn(Collections.singletonList(anEvent));

        List<Event> events = eventService.allEvents();

        assertThat(events).containsOnlyOnce(anEvent);
    }


    @Test
    public void whenTheEventIsCreatedItSendsMailsToTheGuests() {
        organizer = userFactory.user();
        User guest = userFactory.user();
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(guest), organizer);

        eventService.createEvent(anEvent);

        assertThat(guest.invitations()).isNotEmpty();
    }


}