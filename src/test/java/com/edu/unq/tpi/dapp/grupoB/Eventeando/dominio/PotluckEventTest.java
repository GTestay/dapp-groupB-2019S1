package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PotluckEventTest extends EventTest {

    private String description;

    @Before
    public void setUp() {
        description = "alta fiesta";
    }

    @Test
    public void aPotluckEventHasAListOfExpensesToCoverByGuests() {
        PotluckEvent potluckEvent = newPotluckEvent(oneAssistant(), twoExpenses());

        assertEquals(2, potluckEvent.expenses().size());
        assertEquals(1, potluckEvent.guests().size());
        assertEquals(200, potluckEvent.totalCost(), 0);
        assertTrue(potluckEvent.coveredExpenses().isEmpty());
    }

    private PotluckEvent newPotluckEvent(List<User> guests, Map<String, Double> expenses) {
        return Event.createPotluck(organizer(), description, guests, expenses);
    }

    private PotluckEvent newPotluckEvent(User guest, Map<String, Double> expenses) {
        return Event.createPotluck(organizer(), description, Collections.singletonList(guest), expenses);
    }

    @Test
    public void inAPotluckEventAGuestTakeChargeOfAnExpense() {
        Map<String, Double> expenses = twoExpenses();
        String anExpenseName = getAnExpenseName(expenses);
        User guest = newUser();
        PotluckEvent potluckEvent = newPotluckEvent(guest, expenses);

        potluckEvent.aGuestTakeChargeOf(guest, anExpenseName);

        assertEquals(1, potluckEvent.coveredExpenses().size());
        assertTrue(potluckEvent.hasGuestTakeChargeOf(guest, anExpenseName));
    }

    private String getAnExpenseName(Map<String, Double> expenses) {
        return (new ArrayList<>(expenses.keySet())).get(0);
    }

    @Test
    public void inAPotluckEventAGuestCanNotTakeChargeOfAnExpenseThatDoNotExist() {
        String anExpenseName = "An Expense That is not the event";
        User guest = newUser();
        PotluckEvent potluckEvent = newPotluckEvent(guest, twoExpenses());

        try {
            potluckEvent.aGuestTakeChargeOf(guest, anExpenseName);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_EXPENSE_IS_NOT_IN_THE_LIST, e.getMessage());
            assertTrue(potluckEvent.coveredExpenses().isEmpty());
            assertFalse(potluckEvent.hasGuestTakeChargeOf(guest, anExpenseName));
        }
    }

    @Test
    public void inAPotluckEventAGuestCanNotTakeChargeOfAnExpenseThatHasBeenCovered() {
        Map<String, Double> expenses = twoExpenses();
        String anExpenseName = getAnExpenseName(expenses);
        List<User> guests = guests();
        User guestThatHasCoveredAnExpense = guests.get(0);

        PotluckEvent potluckEvent = newPotluckEvent(guests, expenses);
        potluckEvent.aGuestTakeChargeOf(guestThatHasCoveredAnExpense, anExpenseName);

        User aGuestThatCanNotTakeAnExpense = guests.get(1);
        try {
            potluckEvent.aGuestTakeChargeOf(aGuestThatCanNotTakeAnExpense, anExpenseName);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_EXPENSE_IS_ALREADY_COVERED, e.getMessage());
            assertEquals(1, potluckEvent.coveredExpenses().size());
            assertTrue(potluckEvent.hasGuestTakeChargeOf(guestThatHasCoveredAnExpense, anExpenseName));
            assertFalse(potluckEvent.hasGuestTakeChargeOf(aGuestThatCanNotTakeAnExpense, anExpenseName));
        }
    }

    @Test
    public void inAPotluckEventAGuestThatIsNotInvitedCanNotTakeChargeOfAnExpense() {
        Map<String, Double> expenses = twoExpenses();
        String anExpenseNameThatWasAdded = getAnExpenseName(expenses);
        User userThatWasNotInvited = newUser();
        PotluckEvent potluckEvent = newPotluckEvent(oneAssistant(), expenses);

        try {
            potluckEvent.aGuestTakeChargeOf(userThatWasNotInvited, anExpenseNameThatWasAdded);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_THE_USER_WAS_NOT_INVITED, e.getMessage());
            assertTrue(potluckEvent.coveredExpenses().isEmpty());
            assertFalse(potluckEvent.hasGuestTakeChargeOf(userThatWasNotInvited, anExpenseNameThatWasAdded));
        }
    }


}
