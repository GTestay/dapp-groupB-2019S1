package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;

import java.time.LocalDate;

public class UserFactory {

    private Integer usersCreated = 0;

    public User user() {
        User user = User.create(name(), lastname(), email(), genericPassword(), aBirthday());
        this.usersCreated++;
        return user;
    }

    private LocalDate aBirthday() {
        return LocalDate.of(2002, 3, 21);
    }

    private String email() {
        return "genericMail" + this.usersCreated + "@gmail.com";
    }

    public User userWithCash(double cash) {
        User user = user();

        user.cashDeposit(cash);

        return user;
    }

    public static User userIndebt() {
        User user = user();

        Moneylender.get(user).indebt(user);

        return user;
    }

    private String name() {
        return "GenericName";
    }

    private String lastname() {
        return "GenericLastname";
    }

    private String genericPassword() {
        return "P4S5W0RD";
    }
}
