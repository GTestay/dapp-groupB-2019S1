package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
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
    private EventDao eventDao;
    @Autowired
    private EventService eventService;
    private UserFactory userFactory;
    private EventFactory eventFactory;
    private User organizer;


    @Before
    public void setUp() throws Exception {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
    }

    @Test
    public void noneEventIsRetrieved() {
        List<Event> events = eventService.allEvents();
        assertThat(events).isEmpty();
    }

    @Test
    public void anEventIsRetrieved() throws Exception {
        organizer = userFactory.user();
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()), organizer);

        given(this.eventDao.findAll()).willReturn(Collections.singletonList(anEvent));

        List<Event> events = eventService.allEvents();

        assertThat(events).containsOnlyOnce(anEvent);
    }
}