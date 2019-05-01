package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BaquitaCrowdfunding extends EventTest {

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void aBaquitaCrowdfundingInitiallyHasNoFundsAndIsNotCovered() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, oneGuest(), twoExpenses());

        assertEquals(0.0, aBaquitaCrowdfunding.totalFunds(), 0);
        assertFalse(aBaquitaCrowdfunding.isFund());
    }

    @Test
    public void anUserThatWasNotInvitedCanNotAddFundsToTheCrowdfundingEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, guests(), twoExpenses());
        try {
            aBaquitaCrowdfunding.addFunds(user, 100.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_USER_WAS_NOT_INVITED);
            assertEquals(0.0, aBaquitaCrowdfunding.totalFunds(), 0);
            assertFalse(aBaquitaCrowdfunding.isFund());
        }

    }

    @Test
    public void canNotAddANegativeAmountToFundsToTheCrowdfundingEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());
        try {
            aBaquitaCrowdfunding.addFunds(user, -1.0);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_AMOUNT_IS_INVALID);
            assertEquals(0.0, aBaquitaCrowdfunding.totalFunds(), 0);
            assertFalse(aBaquitaCrowdfunding.isFund());
        }

    }

    @Test
    public void anUserCanNotAddMoreFundsInAFullyFundedCrowdfundedEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaCrowdfunding.addFunds(user, aBaquitaCrowdfunding.totalCost());
        try {
            aBaquitaCrowdfunding.addFunds(user, 1.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_CAN_NOT_ADD_MORE_MONEY_THE_CROWDFUNDING_EVENT_IS_FUNDED);
            assertEquals(aBaquitaCrowdfunding.totalCost(), aBaquitaCrowdfunding.totalFunds(), 0);
            assertTrue(aBaquitaCrowdfunding.isFund());
        }

    }

    @Test
    public void anUserCanNotAddMoreFundsThanIsRequiredInTheCrowdfundingEvent() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaCrowdfunding.addFunds(user, aBaquitaCrowdfunding.totalCost() - 1);
        try {
            aBaquitaCrowdfunding.addFunds(user, 2.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT);
            assertEquals(aBaquitaCrowdfunding.totalCost() - 1, aBaquitaCrowdfunding.totalFunds(), 0);
            assertFalse(aBaquitaCrowdfunding.isFund());
        }
    }

    @Test
    public void oneGuestOfTheBaquitaCrowdfundedAddFunds() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaCrowdfunding.addFunds(user, 100.00);
        assertEquals(100.0, aBaquitaCrowdfunding.totalFunds(), 0);
        assertFalse(aBaquitaCrowdfunding.isFund());
    }

    @Test
    public void oneGuestOfTheBaquitaCrowdfundedAddMoreMoneyAndTheEventIsFullyFunded() {
        User user = this.userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = Event.createBaquitaCrowdfunding(organizer(), description, Collections.singletonList(user), twoExpenses());

        aBaquitaCrowdfunding.addFunds(user, aBaquitaCrowdfunding.totalCost());
        assertEquals(200.0, aBaquitaCrowdfunding.totalFunds(), 0);
        assertTrue(aBaquitaCrowdfunding.isFund());
    }

}
