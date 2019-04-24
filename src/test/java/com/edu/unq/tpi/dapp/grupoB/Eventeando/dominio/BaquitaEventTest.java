package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class BaquitaEventTest extends EventTest{

    private String description;

    @Before
    public void setUp() throws Exception {
        description = "alta fiesta";
    }

    @Test
    public void baquitaValidations() {
        assertThatCanNotBeCreatedWithoutOrganizer();
        assertThatCanNotBeCreatedWithoutAssistants();
    }

    private void assertThatCanNotBeCreatedWithoutAssistants() {
        try {
            BaquitaEvent.create(newOrganizer(), description, twoExpenses(), null);
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }

        try {
            BaquitaEvent.create(newOrganizer(), description, twoExpenses(), new ArrayList<>());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }
    }

    private void assertThatCanNotBeCreatedWithoutOrganizer() {
        try {
            BaquitaEvent.create(null, description, twoExpenses(), oneAssistant());
            fail();
        } catch (EventException error) {
            assertEquals(error.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_ORGANIZER);
        }
    }


    @Test
    public void aBaquitaEventIsCreatedWithOrganizerExpensesAndAssistants() {
        User organizer = newOrganizer();

        BaquitaEvent baquita = newbaquitaWithExpensesAndAssistants(organizer, description);

        assertEquals(organizer, baquita.organizer());
        assertEquals(description, baquita.description());
        assertEquals(200.00, baquita.expensesTotalCost(), 0);
    }

    private BaquitaEvent newbaquitaWithExpensesAndAssistants(User organizer, String description) {
        return BaquitaEvent.create(organizer, description, twoExpenses(), oneAssistant());
    }

    @Test
    public void theEventCostIsDividedByTheNumberOfAssistanceAndTheOrganizer() {
        User organizer = newOrganizer();

        BaquitaEvent baquita = newbaquitaWithExpensesAndAssistants(organizer, description);
        assertEquals(100.00, baquita.costPerAssitance(), 0);
    }


}
