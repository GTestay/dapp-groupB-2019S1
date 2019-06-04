package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Party;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventDaoTest {

    @Autowired
    private EventDao eventDao;
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
        List<Event> events = eventDao.findAll();
        assertThat(events).isEmpty();
    }

    @Test
    public void anEventIsRetrieved() throws Exception {
        organizer = userFactory.user();
        Party anEvent = eventFactory.partyWithGuests(userFactory.someUsers(), organizer);
        eventDao.save(anEvent);
        List<Event> events = eventDao.findAll();

        assertThat(events).containsOnlyOnce(anEvent);
    }
}