package com.edu.unq.tpi.dapp.grupoB.Eventeando.factory;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class UserFactory {

    private Faker faker = new Faker();

    private String email() {
        return faker.internet().emailAddress();
    }

    public User userWithCash(double cash, AccountManager accountManager) {
        User user = user();

        user.cashDeposit(cash, accountManager);

        return user;
    }

    private String name() {
        return faker.name().firstName();
    }

    private String password() {
        return faker.internet().password(4, 10);
    }

    private String lastname() {
        return faker.name().lastName();
    }

    private LocalDate birthday() {
        return faker.date().birthday()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public User userIndebt() {
        User user = user();

        user.withDebt();

        return user;
    }

    public User user() {
        return User.create(name(), lastname(), email(), password(), birthday());
    }

    public List<User> someUsers() {
        return Arrays.asList(user(), user());
    }
}
