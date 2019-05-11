package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserFactory {

    private Integer usersCreated = 0;

    private String email() {
        return "genericMail" + usersCreated + "@gmail.com";
    }

    public User userWithCash(double cash) {
        User user = user();

        user.cashDeposit(cash);

        return user;
    }

    private String name() {
        return "GenericName";
    }

    private String password() {
        return "P4S5W0RD";
    }

    private String lastname() {
        return "GenericLastname";
    }

    private LocalDate birthday() {
        return LocalDate.of(2002, 3, 21);
    }

    public User userIndebt() {
        User user = user();

        Moneylender.get().indebt(user);

        return user;
    }

    public User user() {
        User user = User.create(name(), lastname(), email(), password(), birthday());

        usersCreated += 1;
        return user;
    }

    public List<User> someUsers() {
        return Arrays.asList(user(), user());
    }
}
