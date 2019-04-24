package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotluckEvent extends Event {


    private HashMap<String, User> coveredExpenses = new HashMap<>();

    public static PotluckEvent create(User organizer, String description, List<User> assistant, Map<String, Double> expenses) {

        return (PotluckEvent) validateInstance(new PotluckEvent(), organizer, description, expenses, assistant);
    }

    public List<String> coveredExpenses() {
        return new ArrayList<>(this.coveredExpenses.keySet());
    }

    public void aGuestTakeChargeOf(User guest, String anExpense) {
        validateThatTheUserWasInvited(guest.email());
        validateExistanceOfExpense(anExpense);
        validateThatExpenseIsNotAlreadyCovered(anExpense);
        this.coveredExpenses.put(anExpense, guest);
    }

    private void validateThatExpenseIsNotAlreadyCovered(String anExpense) {
        if (expenseIsCovered(anExpense)) {
            throw new EventException(EventValidator.ERROR_EXPENSE_IS_ALREADY_COVERED);
        }
    }

    private void validateExistanceOfExpense(String anExpense) {
        if (!this.expenses.containsKey(anExpense)) {
            throw new EventException(EventValidator.ERROR_EXPENSE_IS_NOT_IN_THE_LIST);
        }
    }

    public boolean hasGuestTakeChargeOf(User guest, String anExpense) {
        return expenseIsCovered(anExpense) && userHasCoveredAnExpense(guest, anExpense);
    }

    private boolean expenseIsCovered(String anExpense) {
        return this.coveredExpenses.containsKey(anExpense);
    }

    /** Precondition: the expense has been check that is covered */
    private boolean userHasCoveredAnExpense(User guest, String anExpense) {
        return this.coveredExpenses.get(anExpense).equals(guest);
    }
}
