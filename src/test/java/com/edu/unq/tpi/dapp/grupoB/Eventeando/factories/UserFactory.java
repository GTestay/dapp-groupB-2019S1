package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserFactory {

    private static Integer usersCreated = 0;

    public static User user() {
        User user = User.create(name(), lastname(), email(), password(), birthday());

        usersCreated = usersCreated + 1;

        return user;
    }

    private static LocalDate birthday() {
        return LocalDate.of(2002, 3, 21);
    }

    private static String email() {
        return "genericMail" + usersCreated + "@gmail.com";
    }

    public static User userWithCash(double cash) {
        User user = user();

        user.cashDeposit(cash);

        return user;
    }

    public static User userIndebt() {
        User user = user();

        Moneylender.get().indebt(user);

        return user;
    }

    private static String name() {
        return "GenericName";
    }

    private static String lastname() {
        return "GenericLastname";
    }

    private static String password() {
        return "P4S5W0RD";
    }

    public List<User> someUsers() {
        return Arrays.asList(this.user(), this.user());
    }
}
