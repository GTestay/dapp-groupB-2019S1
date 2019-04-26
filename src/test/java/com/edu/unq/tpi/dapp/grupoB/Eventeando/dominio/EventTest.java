package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventTest {

    public List<User> oneAssistant() {
        ArrayList<User> users = new ArrayList<>();

        User user = UserFactory.user();
        users.add(user);
        return users;
    }

    public List<User> guests() {
        ArrayList<User> users = new ArrayList<>();
        User user = UserFactory.user();
        User user2 = UserFactory.user();
        users.add(user);
        users.add(user2);
        return users;
    }

    protected Map<String, Double> twoExpenses() {
        HashMap<String, Double> expenses = new HashMap<>();
        expenses.put("Coca 3L", 100.00);
        expenses.put("Sanguchitos x12", 100.00);
        return expenses;
    }

    public User organizer() {
        return User.create("Maximo", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
    }

    public User newUser() {
        return User.create("Pepe", "Allido", "pepe@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
    }

}
