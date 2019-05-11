package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PotluckEventTest extends EventTest {


    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
    }

    @Test
    public void aPotluckEventHasAListOfExpensesToCoverByGuests() {
        PotluckEvent potluckEvent = newPotluckEvent(oneGuest(), twoExpenses());

        assertEquals(2, potluckEvent.expenses().size());
        assertEquals(1, potluckEvent.guests().size());
        assertEquals(200, potluckEvent.totalCost(), 0);
        assertTrue(potluckEvent.coveredExpenses().isEmpty());
    }

    private PotluckEvent newPotluckEvent(List<User> guests, List<Expense> expenses) {
        return Event.createPotluck(organizer(), description, guests, expenses);
    }

    private PotluckEvent newPotluckEvent(User guest, List<Expense> expenses) {
        return Event.createPotluck(organizer(), description, Collections.singletonList(guest), expenses);
    }

    @Test
    public void inAPotluckEventAGuestTakeChargeOfAnExpense() {
        List<Expense> expenses = twoExpenses();
        Expense anExpense = expenses.get(0);
        User guest = newUser();
        PotluckEvent potluckEvent = newPotluckEvent(guest, expenses);

        potluckEvent.aGuestTakeChargeOf(guest, anExpense);

        assertEquals(1, potluckEvent.coveredExpenses().size());
        assertTrue(potluckEvent.hasGuestTakeChargeOf(guest, anExpense));
    }

    private String getAnExpenseName(List<Expense> expenses) {
        return expenses.get(0).name();
    }

    @Test
    public void inAPotluckEventAGuestCanNotTakeChargeOfAnExpenseThatDoNotExist() {
        Expense anExpenseName = Expense.create("An expense that is not added", 100.0);
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
        List<Expense> expenses = twoExpenses();
        Expense anExpense = expenses.get(0);

        List<User> guests = guests();
        User guestThatHasCoveredAnExpense = guests.get(0);

        PotluckEvent potluckEvent = newPotluckEvent(guests, expenses);
        potluckEvent.aGuestTakeChargeOf(guestThatHasCoveredAnExpense, anExpense);

        User aGuestThatCanNotTakeAnExpense = guests.get(1);
        try {
            potluckEvent.aGuestTakeChargeOf(aGuestThatCanNotTakeAnExpense, anExpense);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_EXPENSE_IS_ALREADY_COVERED, e.getMessage());
            assertEquals(1, potluckEvent.coveredExpenses().size());
            assertTrue(potluckEvent.hasGuestTakeChargeOf(guestThatHasCoveredAnExpense, anExpense));
            assertFalse(potluckEvent.hasGuestTakeChargeOf(aGuestThatCanNotTakeAnExpense, anExpense));
        }
    }

    @Test
    public void inAPotluckEventAGuestThatIsNotInvitedCanNotTakeChargeOfAnExpense() {
        List<Expense> expenses = twoExpenses();
        Expense anExpenseThatWasAdded = expenses.get(0);

        User userThatWasNotInvited = newUser();
        PotluckEvent potluckEvent = newPotluckEvent(oneGuest(), expenses);

        try {
            potluckEvent.aGuestTakeChargeOf(userThatWasNotInvited, anExpenseThatWasAdded);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_THE_USER_WAS_NOT_INVITED, e.getMessage());
            assertTrue(potluckEvent.coveredExpenses().isEmpty());
            assertFalse(potluckEvent.hasGuestTakeChargeOf(userThatWasNotInvited, anExpenseThatWasAdded));
        }
    }


}
