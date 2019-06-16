package com.edu.unq.tpi.dapp.grupoB.Eventeando.listener;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Profile({"prod"})
@Transactional
public class Seeds implements ApplicationRunner {

    private UserDao userDao;
    private UserFactory userFactory;
    private ExpenseDao expenseDao;
    private EventDao eventDao;
    private EventFactory eventFactory;

    @Autowired
    public Seeds(UserDao userDao, ExpenseDao expenseDao, EventDao eventDao) {
        this.userDao = userDao;
        this.expenseDao = expenseDao;
        this.eventDao = eventDao;
        eventFactory = new EventFactory();
        userFactory = new UserFactory();
    }

    @Override
    public void run(ApplicationArguments args) {
        seedUsersTable();
        saveEvents();
    }

    public void saveEvents() {

        List<Expense> expenses = eventFactory.expenses();
        expenseDao.saveAll(expenses);

        seedPartyTable(expenses);
        saveBaquitaSharedTable(expenses);
        saveCrowdFundingTable(expenses);
        savePotluckTable(expenses);
    }

    public void savePotluckTable(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        PotluckEvent potluckEvent = eventFactory.potluckWithGuests(guests, seedUsersTable(), expenses);
        eventDao.save(potluckEvent);
    }

    public void saveCrowdFundingTable(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        BaquitaCrowdFundingEvent baquitaCrowdFundingEvent = eventFactory.baquitaCrowfunding(seedUsersTable(), guests, expenses);
        eventDao.save(baquitaCrowdFundingEvent);
    }

    public void saveBaquitaSharedTable(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        BaquitaSharedExpensesEvent baquitaSharedExpensesEvent = eventFactory.baquitaSharedExpenses(seedUsersTable(), guests, expenses);
        eventDao.save(baquitaSharedExpensesEvent);
    }

    private void seedPartyTable(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        Party party = eventFactory.partyWithGuests(guests, seedUsersTable(), expenses);
        eventDao.save(party);
    }

    private User seedUsersTable() {
        User newUser = userFactory.user();
        userDao.save(newUser);
        return newUser;
    }
}
