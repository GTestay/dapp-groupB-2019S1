package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@DiscriminatorValue("Potluck")
public class PotluckEvent extends Event {

    @Transient
    @JsonIgnore
    protected HashMap<Expense, User> coveredExpenses = new HashMap<>();

    @JsonCreator
    public static PotluckEvent create
    (
        @JsonProperty("organizer") User organizer,
        @JsonProperty("description") String description,
        @JsonProperty("guests") List<User> guests,
        @JsonProperty("expenses") List<Expense> expenses
    )
    {
        return validateEvent(new PotluckEvent(), organizer, description, expenses, guests);
    }

    @ManyToMany
    public List<Expense> coveredExpenses() {
        return new ArrayList<>(this.coveredExpenses.keySet());
    }

    public void aGuestTakeChargeOf(User guest, Expense anExpense) {
        validateThatTheUserWasInvited(guest.email());
        validateExistanceOfExpense(anExpense);
        validateThatExpenseIsNotAlreadyCovered(anExpense);
        this.coveredExpenses.put(anExpense, guest);
    }

    private void validateThatExpenseIsNotAlreadyCovered(Expense anExpense) {
        if (expenseIsCovered(anExpense)) {
            throwEventException(EventValidator.ERROR_EXPENSE_IS_ALREADY_COVERED);
        }
    }

    private void validateExistanceOfExpense(Expense anExpense) {
        if (!this.expenses.contains(anExpense)) throwEventException(EventValidator.ERROR_EXPENSE_IS_NOT_IN_THE_LIST);
    }

    public boolean hasGuestTakeChargeOf(User guest, Expense anExpense) {
        return expenseIsCovered(anExpense) && userHasCoveredAnExpense(guest, anExpense);
    }

    private boolean expenseIsCovered(Expense anExpense) {
        return this.coveredExpenses.containsKey(anExpense);
    }

    /**
     * Precondition: the expense has been check that is covered
     */
    private boolean userHasCoveredAnExpense(User guest, Expense anExpense) {
        return this.coveredExpenses.get(anExpense).equals(guest);
    }
}
