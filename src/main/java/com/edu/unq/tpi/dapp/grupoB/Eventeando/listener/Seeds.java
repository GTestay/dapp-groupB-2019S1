package com.edu.unq.tpi.dapp.grupoB.Eventeando.listener;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Profile("dev")
@Transactional
public class Seeds implements ApplicationRunner {

    private UserDao userDao;
    private UserFactory userFactory;
    private ExpenseDao expenseDao;
    private EventDao eventDao;
    private EventFactory eventFactory;
    private AccountManagerService accountManagerService;

    @Autowired
    public Seeds(UserDao userDao, ExpenseDao expenseDao, EventDao eventDao, AccountManagerService accountManagerService) {
        this.userDao = userDao;
        this.expenseDao = expenseDao;
        this.eventDao = eventDao;
        this.accountManagerService = accountManagerService;
        eventFactory = new EventFactory();
        userFactory = new UserFactory();
    }

    @Override
    public void run(ApplicationArguments args) {
        seedUsers();
        seedEvents();
    }

    public void seedEvents() {

        List<Expense> expenses = eventFactory.expenses();
        expenseDao.saveAll(expenses);

        createMartin();
        createGaston();

        seedParties(expenses);
        seedBaquitasShared(expenses);
        seedCrowdFundings(expenses);
        seedPotlucks(expenses);
    }

    protected void createMartin() {
        User martin  = User.create("Martin Ezequiel", "Gonzalez", "martinegonzalez95@gmail.com", "P4SSW0RD", userFactory.birthday());
        userDao.save(martin);
        martin.cashDeposit(500.00, accountManagerService);
    }

    protected void createGaston() {
        User gaston  = User.create("Gaston Ezequiel", "Testay", "gaston.testay@gmail.com", "P4SSW0RD", userFactory.birthday());
        userDao.save(gaston);
        gaston.cashDeposit(750.00, accountManagerService);
    }

    public void seedPotlucks(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        PotluckEvent potluckEvent = eventFactory.potluckWithGuests(guests, seedUsers(), expenses);
        eventDao.save(potluckEvent);
    }

    public void seedCrowdFundings(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        BaquitaCrowdFundingEvent baquitaCrowdFundingEvent = eventFactory.baquitaCrowfunding(seedUsers(), guests, expenses);
        eventDao.save(baquitaCrowdFundingEvent);
    }

    public void seedBaquitasShared(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        BaquitaSharedExpensesEvent baquitaSharedExpensesEvent = eventFactory.baquitaSharedExpenses(seedUsers(), guests, expenses);
        eventDao.save(baquitaSharedExpensesEvent);
    }

    private void seedParties(List<Expense> expenses) {
        List<User> guests = userFactory.someUsers();
        userDao.saveAll(guests);

        Party party = eventFactory.partyWithGuests(guests, seedUsers(), expenses);
        eventDao.save(party);
    }

    private User seedUsers() {
        User newUser = userFactory.user();
        userDao.save(newUser);
        return newUser;
    }
}
