package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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


    /**
     * Baquita Crowdfunding
     */

    @Test
    public void aBaquitaLikeCrowdfundingInitiallyHasNoFundsAndIsNotCovered() {
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, oneGuest(), twoExpenses());

        assertEquals(0.0, aBaquitaLikeCrowdfunding.totalFunds(), 0);
        assertFalse(aBaquitaLikeCrowdfunding.isFund());
    }

    @Test
    public void anUserThatWasNotInvitedCanNotAddFundsToTheCrowdfundingEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, guests(), twoExpenses());
        try {
            aBaquitaLikeCrowdfunding.addFunds(user, 100.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_USER_WAS_NOT_INVITED);
            assertEquals(0.0, aBaquitaLikeCrowdfunding.totalFunds(), 0);
            assertFalse(aBaquitaLikeCrowdfunding.isFund());
        }

    }

    @Test
    public void canNotAddANegativeAmountToFundsToTheCrowdfundingEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());
        try {
            aBaquitaLikeCrowdfunding.addFunds(user, -1.0);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_AMOUNT_IS_INVALID);
            assertEquals(0.0, aBaquitaLikeCrowdfunding.totalFunds(), 0);
            assertFalse(aBaquitaLikeCrowdfunding.isFund());
        }

    }

    @Test
    public void anUserCanNotAddMoreFundsInAFullyFundedCrowdfundedEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaLikeCrowdfunding.addFunds(user, aBaquitaLikeCrowdfunding.totalCost());
        try {
            aBaquitaLikeCrowdfunding.addFunds(user, 1.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_CROWDFUNDED_EVENT_IS_FULLY_FUNDED);
            assertEquals(aBaquitaLikeCrowdfunding.totalCost(), aBaquitaLikeCrowdfunding.totalFunds(), 0);
            assertTrue(aBaquitaLikeCrowdfunding.isFund());
        }

    }

    @Test
    public void anUserCanNotAddMoreFundsThanIsRequiredInTheCrowdfundingEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaLikeCrowdfunding.addFunds(user, aBaquitaLikeCrowdfunding.totalCost() - 1);
        try {
            aBaquitaLikeCrowdfunding.addFunds(user, 2.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT);
            assertEquals(aBaquitaLikeCrowdfunding.totalCost() - 1, aBaquitaLikeCrowdfunding.totalFunds(), 0);
            assertFalse(aBaquitaLikeCrowdfunding.isFund());
        }
    }

    @Test
    public void oneGuestOfTheBaquitaCrowdfundedAddFunds() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaLikeCrowdfunding.addFunds(user, 100.00);
        assertEquals(100.0, aBaquitaLikeCrowdfunding.totalFunds(), 0);
        assertFalse(aBaquitaLikeCrowdfunding.isFund());
    }

    @Test
    public void oneGuestOfTheBaquitaCrowdfundedAddMoreMoneyAndTheEventIsFullyFunded() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaLikeCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaLikeCrowdfunding.addFunds(user, aBaquitaLikeCrowdfunding.totalCost());
        assertEquals(200.0, aBaquitaLikeCrowdfunding.totalFunds(), 0);
        assertTrue(aBaquitaLikeCrowdfunding.isFund());
    }
}
