package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PartyTest extends EventTest {

    private User organizer;
    private LocalDateTime anInvitationLimitDate;
    private Double pricePerAssistant;
    private LocalDateTime confirmationDate;
    private EventFactory eventFactory;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
        organizer = organizer();
        anInvitationLimitDate = eventFactory.anInvitationLimitDate();
        pricePerAssistant = 100.0;
        confirmationDate = eventFactory.confirmationDate();
    }

    @Test
    public void aPartyCanBeCreatedWithAnOrganizerAListOfGuestsAndALimitConfirmationDate() {
        Party pepitoParty = partyWithGuests();

        assertEquals(organizer, pepitoParty.organizer());
        assertFalse(pepitoParty.guests().isEmpty());
        assertEquals(anInvitationLimitDate, pepitoParty.invitationLimitDate());
    }


    private Party partyWithGuests() {
        return Party.create(organizer, description, this.guests(), new ArrayList<>(), anInvitationLimitDate);
    }

    @Test
    public void aPartyCanNotBeCreatedWithoutGuests() {
        try {
            Party.create(organizer, description, new ArrayList<>(), new ArrayList<>(), anInvitationLimitDate);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }

    }

    private void assertThatThePriceOfSuppliesOfAPartyIs(int price, Party party) {
        assertEquals(price, party.expensesTotalCost(), 0);
    }

    @Test
    public void theSuppliesCostOfAPartyWithoutSuppliesIsZero() {
        assertThatThePriceOfSuppliesOfAPartyIs(0, partyWithGuests());
    }

    @Test
    public void aPartyCanAddASupply() {
        Party partyDePepito = partyWithGuests();

        partyDePepito.addExpense(eventFactory.coca());
        partyDePepito.addExpense(eventFactory.sanguchitos());

        assertThatThePriceOfSuppliesOfAPartyIs(200, partyDePepito);
    }

    @Test
    public void theCostOfAPartyWithoutConfirmationsAndSuppliesIsOfZero() {
        assertThatTheFullCostOfThePartyIs(partyWithGuests(), 0);
    }

    @Test
    public void theTotalCostOfAPartyWIthoutAssistantsAndWithSuppliesIsZero() {
        Party partyWithSupplies = partyWithGuests();

        partyWithSupplies.addExpense(eventFactory.coca());

        assertThatTheFullCostOfThePartyIs(partyWithSupplies, 0);
    }

    @Test
    public void aUsertThatIsNotInvitedCanNotConfirmAssistance() {
        Party party = partyWithGuests();
        try {
            party.confirmAssistance("unEmailQueNoEstaInvitado@gmail.com", confirmationDate);
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_USER_WAS_NOT_INVITED);
            assertThatTheFullCostOfThePartyIs(party, 0);
        }
    }

    @Test
    public void anInvitedUserCanNotConfirmAssistanceBecauseTheDateTimeLimit() {
        Party party = partyWithGuestsAndCostPerAssistance();
        List<User> guests = party.guests();
        User invitedUser = guests.get(0);
        try {
            party.confirmAssistance(invitedUser.email(), eventFactory.invalidConfirmationDate());
            fail();
        } catch (EventException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT);
            assertThatTheFullCostOfThePartyIs(party, 0);
        }
    }

    @Test
    public void thePartyCanCalculateTheCostWithSuppliesAndConfirmations() {
        List<User> guests = guests();
        Party party = eventFactory.partyWithGuestsExpensesAndAPricePerAssistant(guests, eventFactory.expenses(), organizer);
        User aUserThatIsInvited = guests.get(0);
        User anotherUserThatIsInvited = guests.get(1);

        party.confirmAssistance(aUserThatIsInvited.email(), confirmationDate);
        party.confirmAssistance(anotherUserThatIsInvited.email(), confirmationDate);

        assertThatTheFullCostOfThePartyIs(party, 400.00);
    }

    private Party partyWithGuestsAndCostPerAssistance() {
        return eventFactory.partyWithGuestsExpensesAndAPricePerAssistant(guests(), eventFactory.expenses(), organizer);
    }

    private void assertThatTheFullCostOfThePartyIs(Party party, double totalCostOfTheParty) {
        assertEquals(totalCostOfTheParty, party.totalCost(), 0);
    }
}
