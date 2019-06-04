package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.UserNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userDao.findById(id)
                .orElseThrow(this::notFound);
    }

    private RuntimeException notFound() {
        return new UserNotFound(messageUserNotFound());
    }

    public static String messageUserNotFound() {
        return "User not found";
    }

    public User findUserByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(this::notFound);
    }

    private List<User> findUsersByEmails(List<String> guestsEmails) {
        return userDao.findAllByEmailIn(guestsEmails);
    }

    public List<User> obtainUsersFromEmails(List<String> guestsEmails) {
        List<User> guests = findUsersByEmails(guestsEmails);
        if (guestsEmails.size() != guests.size()) {
            throw new UserNotFound(usersNotFoundFromEmails());
        }
        return guests;
    }

    public static String usersNotFoundFromEmails() {
        return "There are no users with the emails given";
    }
}
