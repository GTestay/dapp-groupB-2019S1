package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class BaquitaSharedExpensesEventTest extends EventTest {

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
    }

    @Test
    public void baquitaValidations() {
        assertThatCanNotBeCreatedWithoutOrganizer();
        assertThatCanNotBeCreatedWithoutGuests();
    }

    private void assertThatCanNotBeCreatedWithoutGuests() {
        try {
            BaquitaSharedExpensesEvent.create(userFactory.user(), description, null, twoExpenses());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }

        try {
            BaquitaSharedExpensesEvent.create(organizer(), description, new ArrayList<>(), twoExpenses());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }
    }

    private void assertThatCanNotBeCreatedWithoutOrganizer() {
        try {
            BaquitaSharedExpensesEvent.create(null, description, oneGuest(), twoExpenses());
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
        return BaquitaSharedExpensesEvent.create(organizer, description, oneGuest(), twoExpenses());
    }

    @Test
    public void theEventCostIsDividedByTheNumberOfAssistanceAndTheOrganizer() {
        User organizer = organizer();

        BaquitaSharedExpensesEvent baquita = newbaquitaWithExpensesAndGuests(organizer, description);
        assertEquals(100.00, baquita.costPerAssitance(), 0);
    }
}
