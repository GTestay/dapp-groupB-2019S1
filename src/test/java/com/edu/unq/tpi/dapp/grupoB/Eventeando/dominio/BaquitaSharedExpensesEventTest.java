package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class BaquitaSharedExpensesEventTest extends EventTest {

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void baquitaValidations() {
        assertThatCanNotBeCreatedWithoutOrganizer();
        assertThatCanNotBeCreatedWithoutGuests();
    }

    private void assertThatCanNotBeCreatedWithoutGuests() {
        try {
            Event.createBaquita(userFactory.user(), description, null, twoExpenses());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }

        try {
            Event.createBaquita(organizer(), description, new ArrayList<>(), twoExpenses());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }
    }

    private void assertThatCanNotBeCreatedWithoutOrganizer() {
        try {
            Event.createBaquita(null, description, oneGuest(), twoExpenses());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_ORGANIZER);
        }
    }

    @Test
    public void aBaquitaEventIsCreatedWithOrganizerExpensesAndGuests() {
        User organizer = organizer();

        BaquitaSharedExpensesEvent baquita = newbaquitaWithExpensesAndGuests(organizer, description);

        assertEquals(organizer, baquita.organizer());
        assertEquals(description, baquita.description());
        assertEquals(200.00, baquita.expensesTotalCost(), 0);
    }

    private BaquitaSharedExpensesEvent newbaquitaWithExpensesAndGuests(User organizer, String description) {
        return Event.createBaquita(organizer, description, oneGuest(), twoExpenses());
    }

    @Test
    public void theEventCostIsDividedByTheNumberOfAssistanceAndTheOrganizer() {
        User organizer = organizer();

        BaquitaSharedExpensesEvent baquita = newbaquitaWithExpensesAndGuests(organizer, description);
        assertEquals(100.00, baquita.costPerAssitance(), 0);
    }
}
