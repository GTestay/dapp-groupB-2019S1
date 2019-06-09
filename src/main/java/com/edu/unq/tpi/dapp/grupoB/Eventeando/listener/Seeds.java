package com.edu.unq.tpi.dapp.grupoB.Eventeando.listener;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
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
    private EventDao eventDao;
    private EventFactory eventFactory;

    @Autowired
    public Seeds(UserDao userDao, EventDao eventDao) {
        this.userDao = userDao;
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

        seedPartyTable();
        saveBaquitaSharedTable();
        saveCrowdFundingTable();
        savePotluckTable();
    }

    public void savePotluckTable() {
        User user = userFactory.user();
        List<User> guests = userFactory.someUsers();

        PotluckEvent potluckEvent = eventFactory.potluckWithGuests(guests, user);
        eventDao.save(potluckEvent);
    }

    public void saveCrowdFundingTable() {
        User user = userFactory.user();
        List<User> guests = userFactory.someUsers();

        BaquitaCrowdFundingEvent baquitaCrowdFundingEvent = eventFactory.baquitaCrowfunding(user, guests);
        eventDao.save(baquitaCrowdFundingEvent);
    }

    public void saveBaquitaSharedTable() {
        User user = userFactory.user();
        List<User> guests = userFactory.someUsers();
        BaquitaSharedExpensesEvent baquitaSharedExpensesEvent = eventFactory.baquitaSharedExpenses(user, guests);
        eventDao.save(baquitaSharedExpensesEvent);
    }

    private void seedPartyTable() {
        User user = userFactory.user();
        List<User> guests = userFactory.someUsers();
        Party party = eventFactory.partyWithGuests(guests, user);
        eventDao.save(party);
    }

    private void seedUsersTable() {
        User newUser = userFactory.user();
        userDao.save(newUser);
    }
}
