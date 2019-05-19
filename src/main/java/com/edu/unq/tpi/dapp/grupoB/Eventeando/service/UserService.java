package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.UserNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(User newUserToCreate) {
        return userDao.save(newUserToCreate);
    }

    public User searchUser(Long id) {
        return userDao.findById(id).orElseThrow(this::notFound);
    }

    private RuntimeException notFound() {
        return new UserNotFound(messageUserNotFound());
    }

    public static String messageUserNotFound() {
        return "User not found";
    }

}
