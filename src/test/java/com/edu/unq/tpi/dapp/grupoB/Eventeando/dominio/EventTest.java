package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;

import java.util.*;

public abstract class EventTest {

    protected String description = "Alta fiesta";
    protected UserFactory userFactory;


    public List<User> oneGuest() {
        return Collections.singletonList(userFactory.user());
    }

    public List<User> guests() {
        return Arrays.asList(userFactory.user(), userFactory.user());
    }

    protected Map<String, Double> twoExpenses() {
        HashMap<String, Double> expenses = new HashMap<>();
        expenses.put("Coca 3L", 100.00);
        expenses.put("Sanguchitos x12", 100.00);
        return expenses;
    }

    public User organizer() {
        return userFactory.user();
    }

    public User newUser() {
        return userFactory.user();
    }
}
