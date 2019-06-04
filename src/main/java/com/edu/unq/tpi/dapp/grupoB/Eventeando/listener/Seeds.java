package com.edu.unq.tpi.dapp.grupoB.Eventeando.listener;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Party;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"prod"})
public class Seeds implements ApplicationRunner {

    private UserDao userDao;
    private UserFactory userFactory = new UserFactory();
    private EventDao eventDao;

    @Autowired
    public Seeds(UserDao userDao, EventDao eventDao) {
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedUsersTable();
        seedEventsTable();
    }

    private void seedEventsTable() {
        EventFactory factory = new EventFactory();

        Party party = factory.partyWithGuests(userFactory.someUsers(), userFactory.user());

        eventDao.save(party);
    }

    private void seedUsersTable() {
        User newUser = userFactory.user();

        userDao.save(newUser);
    }
}
