package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BaquitaCrowdfundingTest extends EventTest {

    private User anUserToInvite;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
        anUserToInvite = userFactory.user();
    }

    @Test
    public void aBaquitaCrowdfundingInitiallyHasNoFundsAndIsNotCovered() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, oneGuest(), twoExpenses());

        assertEquals(0.0, aBaquitaCrowdfunding.totalMoneyRaised(), 0);
        assertFalse(aBaquitaCrowdfunding.isFund());
    }

    @Test
    public void anUserThatWasNotInvitedCanNotAddFundsToTheCrowdfundingEvent() {
        User anUserThatIsNotInvited = userFactory.user();
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, guests(), twoExpenses());
        try {
            aBaquitaCrowdfunding.addFunds(anUserThatIsNotInvited, anAmount());
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_USER_WAS_NOT_INVITED);
            assertEquals(0.0, aBaquitaCrowdfunding.totalMoneyRaised(), 0);
            assertFalse(aBaquitaCrowdfunding.isFund());
            assertFalse(aBaquitaCrowdfunding.hasAddedFunds(anUserToInvite));
        }

    }

    @Test
    public void canNotAddANegativeAmountToFundsToTheCrowdfundingEvent() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, createGuest(anUserToInvite), twoExpenses());
        try {
            double negativeAmount = -1.0;
            aBaquitaCrowdfunding.addFunds(anUserToInvite, negativeAmount);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_AMOUNT_IS_INVALID);
            assertEquals(0.0, aBaquitaCrowdfunding.totalMoneyRaised(), 0);
            assertFalse(aBaquitaCrowdfunding.isFund());
            assertFalse(aBaquitaCrowdfunding.hasAddedFunds(anUserToInvite));
        }

    }

    private List<User> createGuest(User anUser) {
        return Collections.singletonList(anUser);
    }

    @Test
    public void anUserCanNotAddMoreFundsInAFullyFundedCrowdfundedEvent() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, createGuest(anUserToInvite), twoExpenses());

        aBaquitaCrowdfunding.addFunds(anUserToInvite, aBaquitaCrowdfunding.totalCost());
        try {
            aBaquitaCrowdfunding.addFunds(anUserToInvite, 1.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_CAN_NOT_ADD_MORE_MONEY_THE_CROWDFUNDING_EVENT_IS_FUNDED);
            assertEquals(aBaquitaCrowdfunding.totalCost(), aBaquitaCrowdfunding.totalMoneyRaised(), 0);
            assertTrue(aBaquitaCrowdfunding.isFund());
            assertTrue(aBaquitaCrowdfunding.hasAddedFunds(anUserToInvite));
        }

    }

    @Test
    public void anUserCanNotAddMoreFundsThanIsRequiredInTheCrowdfundingEvent() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, createGuest(anUserToInvite), twoExpenses());

        aBaquitaCrowdfunding.addFunds(anUserToInvite, aBaquitaCrowdfunding.totalCost() - 1);
        try {
            aBaquitaCrowdfunding.addFunds(anUserToInvite, 2.00);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_CAN_NOT_ADD_MORE_FUNDS_THAN_IS_REQUIRED_IN_THE_CROWDFUNDING_EVENT);
            assertEquals(aBaquitaCrowdfunding.totalCost() - 1, aBaquitaCrowdfunding.totalMoneyRaised(), 0);
            assertFalse(aBaquitaCrowdfunding.isFund());
            assertTrue(aBaquitaCrowdfunding.hasAddedFunds(anUserToInvite));
        }
    }

    @Test
    public void oneGuestOfTheBaquitaCrowdfundedAddFunds() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, createGuest(anUserToInvite), twoExpenses());

        aBaquitaCrowdfunding.addFunds(anUserToInvite, anAmount());
        assertEquals(100.0, aBaquitaCrowdfunding.totalMoneyRaised(), 0);
        assertTrue(aBaquitaCrowdfunding.hasAddedFunds(anUserToInvite));
        assertFalse(aBaquitaCrowdfunding.isFund());
    }

    private double anAmount() {
        return 100.00;
    }

    @Test
    public void oneGuestOfTheBaquitaCrowdfundedAddMoreMoneyAndTheEventIsFullyFunded() {
        BaquitaCrowdFundingEvent aBaquitaCrowdfunding = BaquitaCrowdFundingEvent.create(organizer(), description, createGuest(anUserToInvite), twoExpenses());

        aBaquitaCrowdfunding.addFunds(anUserToInvite, aBaquitaCrowdfunding.totalCost());
        assertEquals(200.0, aBaquitaCrowdfunding.totalMoneyRaised(), 0);
        assertTrue(aBaquitaCrowdfunding.isFund());
        assertTrue(aBaquitaCrowdfunding.hasAddedFunds(anUserToInvite));
    }

}
