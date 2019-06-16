package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
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
    private ExpenseDao expenseDao;
    @Autowired
    private EventService eventService;
    private UserFactory userFactory;
    private EventFactory eventFactory;
    private User organizer;


    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
        organizer = persistedUser();
    }

    @Test
    public void noneEventIsRetrieved() {
        List<Event> events = eventService.allEvents();
        assertThat(events).isEmpty();
    }

    @Test
    public void anEventIsRetrieved() {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(persistedUser()), organizer, savedExpenses());
        eventDao.save(anEvent);

        List<Event> events = eventService.allEvents();

        assertThat(events).containsOnlyOnce(anEvent);
    }


    @Test
    public void givenAnUserIdAndUserHasNoEvents() {
        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).isEmpty();
    }

    @Test
    public void givenAnUserIdAllEventOfTheUserAreRetrieved() {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(persistedUser()), organizer, savedExpenses());
        eventDao.save(anEvent);

        Event anEventWithTheUserThatIsNotTheOrganizer = eventFactory.partyWithGuests(Collections.singletonList(organizer), persistedUser(), savedExpenses());
        eventDao.save(anEventWithTheUserThatIsNotTheOrganizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).containsOnlyOnce(anEvent);
    }

    @Test
    public void givenAnUserIdAllEventOfTheUserAreRetrievedThatAreOnlyAnOrganizer() {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(persistedUser()), organizer, savedExpenses());
        eventDao.save(anEvent);

        Event anEventWithTheUserThatIsNotTheOrganizer = eventFactory.partyWithGuests(Collections.singletonList(organizer), persistedUser(), savedExpenses());
        eventDao.save(anEventWithTheUserThatIsNotTheOrganizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).containsOnlyOnce(anEvent);
    }

    @Test
    public void whenTheEventIsCreatedItSendsMailsToTheGuests() {
        User guest = persistedUser();

        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(guest), organizer, savedExpenses());

        eventService.sendInvitations(anEvent);

        assertThat(guest.invitations()).isNotEmpty();
    }

    public User persistedUser() {
        User user = userFactory.user();
        userDao.save(user);
        return user;
    }

    public List<Expense> savedExpenses() {
        List<Expense> expenses = eventFactory.expenses();
        expenseDao.saveAll(expenses);
        return expenses;
    }
}