package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
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
    @Autowired
    private ExpenseDao expenseDao;
    @Autowired
    private UserDao userDao;
    private UserFactory userFactory;
    private EventFactory eventFactory;


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
        User organizer = userFactory.user();
        userDao.save(organizer);

        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        Party anEvent = eventFactory.partyWithGuests(guests, organizer, savedExpenses());
        eventDao.save(anEvent);
        List<Event> events = eventDao.findAll();

        assertThat(events).containsOnlyOnce(anEvent);
    }

    public List<Expense> savedExpenses() {

        List<Expense> expenses = eventFactory.expenses();
        expenseDao.saveAll(expenses);
        return expenses;
    }
}