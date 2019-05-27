package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class EventTest {

    protected String description = "Alta fiesta";
    protected UserFactory userFactory;
    protected EventFactory eventFactory;

    public List<User> oneGuest() {
        return Collections.singletonList(userFactory.user());
    }

    public List<User> guests() {
        return Arrays.asList(userFactory.user(), userFactory.user());
    }

    protected List<Expense> twoExpenses() {
        return eventFactory.expenses();
    }

    public User organizer() {
        return userFactory.user();
    }

    public User newUser() {
        return userFactory.user();
    }
}
