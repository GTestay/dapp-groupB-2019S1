package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PotluckEventTest extends EventTest {

    private String description;

    @Before
    public void setUp() {
        description = "alta fiesta";
    }

    private PotluckEvent createPotluck(List<User> users, Map<String, Double> stringDoubleMap) {
        return PotluckEvent.create(newOrganizer(), description, users, stringDoubleMap);
    }

    @Test
    public void aPotluckEventHasAListOfExpenses() {
        PotluckEvent potluckEvent = createPotluck(oneAssistant(), twoExpenses());

        assertEquals(2, potluckEvent.expenses().size());
        assertTrue(potluckEvent.coveredExpenses().isEmpty());
    }

    @Test
    public void inAPotluckEventAGuestTakeChargeOfAnExpense() {
        Map<String, Double> expenses = twoExpenses();
        String anExpense = "Coca 3L";
        List<User> listOfOneAssistant = oneAssistant();
        User guest = listOfOneAssistant.get(0);
        PotluckEvent potluckEvent = createPotluck(listOfOneAssistant, expenses);

        potluckEvent.aGuestTakeChargeOf(guest, anExpense);

        assertEquals(1, potluckEvent.coveredExpenses().size());
        assertTrue(potluckEvent.hasGuestTakeChargeOf(guest, anExpense));
    }

    @Test
    public void inAPotluckEventAGuestCanNotTakeChargeOfAnExpenseThatDoNotExist() {
        Map<String, Double> expenses = twoExpenses();
        String anExpense = "An Expense That is not the event";
        List<User> listOfOneAssistant = oneAssistant();
        User guest = listOfOneAssistant.get(0);
        PotluckEvent potluckEvent = createPotluck(listOfOneAssistant, expenses);
        try {
            potluckEvent.aGuestTakeChargeOf(guest, anExpense);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_EXPENSE_IS_NOT_IN_THE_LIST, e.getMessage());
            assertTrue(potluckEvent.coveredExpenses().isEmpty());
            assertFalse(potluckEvent.hasGuestTakeChargeOf(guest, anExpense));
        }
    }

    @Test
    public void inAPotluckEventAGuestCanNotTakeChargeOfAnExpenseThatHasBeenCovered() {
        String anExpense = "Coca 3L";
        List<User> listOfOneAssistant = assistants();
        User guest = listOfOneAssistant.get(0);
        User aGuestThatCanNotTakeAnExpense = listOfOneAssistant.get(1);
        PotluckEvent potluckEvent = createPotluck(listOfOneAssistant, twoExpenses());
        potluckEvent.aGuestTakeChargeOf(guest, anExpense);

        try {
            potluckEvent.aGuestTakeChargeOf(aGuestThatCanNotTakeAnExpense, anExpense);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_EXPENSE_IS_ALREADY_COVERED, e.getMessage());
            assertEquals(1, potluckEvent.coveredExpenses().size());
            assertTrue(potluckEvent.hasGuestTakeChargeOf(guest, anExpense));
            assertFalse(potluckEvent.hasGuestTakeChargeOf(aGuestThatCanNotTakeAnExpense, anExpense));
        }
    }

    @Test
    public void inAPotluckEventAGuestThatIsNotInvitedCanNotTakeChargeOfAnExpense() {
        Map<String, Double> expenses = twoExpenses();
        String anExpense = "Coca 3L";
        User guest = newUser();
        PotluckEvent potluckEvent = createPotluck(oneAssistant(), expenses);
        try {
            potluckEvent.aGuestTakeChargeOf(guest, anExpense);
            fail();
        } catch (EventException e) {
            assertEquals(EventValidator.ERROR_THE_USER_WAS_NOT_INVITED, e.getMessage());
            assertTrue(potluckEvent.coveredExpenses().isEmpty());
            assertFalse(potluckEvent.hasGuestTakeChargeOf(guest, anExpense));
        }
    }


}
