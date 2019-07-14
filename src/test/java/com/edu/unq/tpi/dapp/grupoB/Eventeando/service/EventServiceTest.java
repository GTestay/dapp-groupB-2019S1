package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Party;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventeandoNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;


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
        Event anEvent = aNewEvent();

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
        Event anEvent = aNewEvent();

        Event anEventWithTheUserThatIsNotTheOrganizer = eventFactory.partyWithGuests(Collections.singletonList(organizer), persistedUser(), savedExpenses());
        eventDao.save(anEventWithTheUserThatIsNotTheOrganizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).containsOnlyOnce(anEvent);
    }

    @Test
    public void givenAnUserIdAllEventOfTheUserAreRetrievedThatAreOnlyAnOrganizer() {
        Event anEvent = aNewEvent();

        Event anEventWithTheUserThatIsNotTheOrganizer = eventFactory.partyWithGuests(Collections.singletonList(organizer), persistedUser(), savedExpenses());
        eventDao.save(anEventWithTheUserThatIsNotTheOrganizer);

        List<Event> events = eventService.allEventsOf(organizer.id());

        assertThat(events).containsOnlyOnce(anEvent);
    }

    @Test
    public void anEventCanNotBeScoredWith0orLess() {
        User anUser = persistedUser();
        Event anEvent = eventWithOneGuest(anUser);


        try {
            eventService.scoreAnEvent(anEvent.id(), anUser.id(), 0);
            fail();
        } catch (RuntimeException e) {
            assertThat(eventService.eventScore(anEvent.id())).isEqualTo(0);
            assertThat(e).hasMessage(EventValidator.ERROR_THE_GIVEN_SCORE_MUST_BE_GREATER_THAN_ZERO);
        }
    }

    @Test
    public void canNotScoreAnEventGivenAnInexistentEvent() {
        User anUser = persistedUser();
        eventWithOneGuest(anUser);

        try {
            eventService.scoreAnEvent((long) -1, anUser.id(), 5);
            fail();
        } catch (EventeandoNotFound e) {
            assertThat(e).hasMessage(EventValidator.EVENT_NOT_FOUND);
        }
    }


    @Test
    public void anEventCanNotBeScoredByTheOrganizer() {
        Event anEvent = aNewEvent();

        try {
            eventService.scoreAnEvent(anEvent.id(), organizer.id(), 1);
            fail();
        } catch (RuntimeException e) {
            assertThat(eventService.eventScore(anEvent.id())).isEqualTo(0);
            assertThat(e).hasMessage(EventValidator.EVENT_CAN_NOT_BE_SCORED_BY_ORGANIZER);
        }
    }


    @Test
    public void anEventCanNotBeScoredByAnInexistentUser() {
        Event anEvent = aNewEvent();

        try {
            eventService.scoreAnEvent(anEvent.id(), (long) -1, 1);
            fail();
        } catch (RuntimeException e) {
            assertThat(eventService.eventScore(anEvent.id())).isEqualTo(0);
            assertThat(e).hasMessage(UserService.messageUserNotFound());
        }
    }

    @Test
    public void anEventCanBeScored() {
        User anUser = persistedUser();
        Event anEvent = eventWithOneGuest(anUser);

        eventService.scoreAnEvent(anEvent.id(), anUser.id(), 5);
        assertThat(eventService.eventScore(anEvent.id())).isEqualTo(5);
    }

    @Test
    public void anEventCanBeScoredByMoreUsers() {
        User anUser = persistedUser();
        User otherUser = persistedUser();
        Event anEvent = eventFactory.partyWithGuests(Arrays.asList(anUser, otherUser), organizer, savedExpenses());
        eventDao.save(anEvent);

        eventService.scoreAnEvent(anEvent.id(), anUser.id(), 1);
        eventService.scoreAnEvent(anEvent.id(), otherUser.id(), 2);
        assertThat(eventService.eventScore(anEvent.id())).isEqualTo(3);
    }


    @Test
    public void anEventCanBeScoredOnlyOncePerUser() {
        User anUser = persistedUser();
        User otherUser = persistedUser();
        Event anEvent = eventFactory.partyWithGuests(Arrays.asList(anUser, otherUser), organizer, savedExpenses());
        eventDao.save(anEvent);

        eventService.scoreAnEvent(anEvent.id(), anUser.id(), 1);
        eventService.scoreAnEvent(anEvent.id(), otherUser.id(), 5);

        eventService.scoreAnEvent(anEvent.id(), anUser.id(), 5);
        assertThat(eventService.eventScore(anEvent.id())).isEqualTo(10);
    }

    @Test
    public void anUserCanScoreMoreThanOneEvent() {
        User anUser = persistedUser();
        Event anEvent = eventWithOneGuest(anUser);
        Event otherEvent = eventWithOneGuest(anUser);

        eventService.scoreAnEvent(anEvent.id(), anUser.id(), 1);
        eventService.scoreAnEvent(otherEvent.id(), anUser.id(), 5);

        assertThat(eventService.eventScore(anEvent.id())).isEqualTo(1);
        assertThat(eventService.eventScore(otherEvent.id())).isEqualTo(5);
    }

    private Party eventWithOneGuest(User anUser) {
        Party event = eventFactory.partyWithGuests(Collections.singletonList(anUser), organizer, savedExpenses());
        eventDao.save(event);
        return event;
    }

    @Test
    public void whenTheEventIsNotScored() {
        aNewEvent();

        assertThat(eventService.popularEvents()).isEmpty();
    }

    @Test
    public void popularEventsAreRankedByScore() {
        User anUser = persistedUser();
        User otherUser = persistedUser();
        Event mostPopular = eventFactory.partyWithGuests(Arrays.asList(anUser, otherUser), organizer, savedExpenses());
        eventDao.save(mostPopular);
        Event aBitPopular = eventWithOneGuest(anUser);
        Event lessPopular = eventWithOneGuest(anUser);
        Event eventWithNoRanking = eventWithOneGuest(persistedUser());

        eventService.scoreAnEvent(aBitPopular.id(), anUser.id(), 5);
        eventService.scoreAnEvent(mostPopular.id(), anUser.id(), 10);
        eventService.scoreAnEvent(lessPopular.id(), anUser.id(), 1);
        eventService.scoreAnEvent(mostPopular.id(), otherUser.id(), 11);

        List<Event> events = eventService.popularEvents();
        assertThat(events.size()).isEqualTo(3);
        assertThat(events).containsExactly(mostPopular, aBitPopular, lessPopular);
    }


    private Event aNewEvent() {
        return eventWithOneGuest(persistedUser());
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