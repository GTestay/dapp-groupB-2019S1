package com.edu.unq.tpi.dapp.grupoB.Eventeando.listener;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Seeds implements ApplicationRunner {

    private UserDao userDao;

    @Autowired
    public Seeds(UserDao userDao) {
        this.userDao = userDao;
    }

    private void seedUsersTable() {
        UserFactory factory = new UserFactory();
        User newUser = factory.user();

        userDao.save(newUser);
    }

    @Override
    public void run(ApplicationArguments args) {
        seedUsersTable();
    }
}
