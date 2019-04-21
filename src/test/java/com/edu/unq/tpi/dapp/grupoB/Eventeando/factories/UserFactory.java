package com.edu.unq.tpi.dapp.grupoB.Eventeando.factories;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;

import java.time.LocalDate;

public class UserFactory {
    public static User user() {
        return User.create("Maximo", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
    }

    public static User userWithCash(double cash) {
        User user = user();

        user.cashDeposit(100.00);

        return user;
    }
}
