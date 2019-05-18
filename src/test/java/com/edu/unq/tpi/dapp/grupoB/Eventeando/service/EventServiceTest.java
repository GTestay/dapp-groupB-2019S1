package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.repository.EventDao;
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

    @Autowired
    private EventService eventService;
    @MockBean
    private EventDao eventDao;


    @Test
    public void noneEventIsRetrieved() {
        List<Event> events = eventService.allEvents();
        assertThat(events).isEmpty();
    }

    @Test
    public void anEventIsRetrieved() throws Exception {
        UserFactory userFactory = new UserFactory();
        EventFactory eventFactory = new EventFactory(userFactory);
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()));

        given(this.eventDao.findAll()).willReturn(Collections.singletonList(anEvent));

        List<Event> events = eventService.allEvents();

        assertThat(events).containsOnlyOnce(anEvent);
    }
}
