package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.EventValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class PartyTest extends EventTest {

    private User organizer;
    private LocalDateTime anInvitationLimitDate;
    private double pricePerAssistant;
    private LocalDateTime confirmationDate;
    private EventFactory eventFactory;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory(userFactory);
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
        return Event.createParty(organizer, description, this.guests(), new HashMap<>(), anInvitationLimitDate, 0.0);
    }

    @Test
    public void aPartyCanNotBeCreatedWithoutGuests() {
        try {
            Event.createParty(organizer, description, new ArrayList<>(), new HashMap<>(), anInvitationLimitDate, 0.0);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), EventValidator.EVENT_IS_INVALID_WITHOUT_GUESTS);
        }

    }

    @Test
    public void aPartyTicketPriceMustNotBeNegative() {
        try {
            Event.createParty(organizer, description, this.guests(), new HashMap<>(), anInvitationLimitDate, -1.0);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), EventValidator.EVENT_TICKET_PRICE_MUST_NOT_BE_NEGATIVE);
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
    public void aPartyCanNotAddASupplyWhoseCostIsNegative() {
        try {
            partyWithGuests().addExpense("Coca de 1 litro", -1.00);
            fail();
        } catch (RuntimeException e) {
            assertThatThePriceOfSuppliesOfAPartyIs(0, partyWithGuests());
        }
    }

    @Test
    public void aPartyCanAddASupply() {
        Party partyDePepito = partyWithGuests();

        partyDePepito.addExpense("Coca de 1 litro", 100.00);
        partyDePepito.addExpense("Sanguches de Miga x 24", 200.00);

        assertThatThePriceOfSuppliesOfAPartyIs(300, partyDePepito);
    }

    @Test
    public void theCostOfAPartyWithoutConfirmationsAndSuppliesIsOfZero() {
        assertThatTheFullCostOfThePartyIs(partyWithGuests(), 0);
    }

    @Test
    public void theCostOfThePartyWithoutSuppliesAndWithConfirmationsIsCalculatedByThePricePerAssistant() {
        Party unaPartyConUnaConfirmacion = eventFactory.partyWithGuestsExpensesAndAPricePerAssistant(this.guests(), pricePerAssistant, eventFactory.noExpenses());
        List<User> guests = unaPartyConUnaConfirmacion.guests();
        User user = guests.get(0);

        unaPartyConUnaConfirmacion.confirmAssistance(user.email(), confirmationDate);

        assertThatTheFullCostOfThePartyIs(unaPartyConUnaConfirmacion, pricePerAssistant);
    }

    @Test
    public void theTotalCostOfAPartyWIthoutAssistantsAndWithSuppliesIsZero() {
        Party partyWithSupplies = partyWithGuests();

        partyWithSupplies.addExpense("Coca de 1 litro", 100.00);

        assertThatTheFullCostOfThePartyIs(partyWithSupplies, 0);
    }

    @Test
    public void aUsertThatIsNotInvitedCanNotConfirmAssistance() {
        Party party = partyWithGuests();
        try {
            party.confirmAssistance("unEmailQueNoEstaInvitado@gmail.com", confirmationDate);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_USER_WAS_NOT_INVITED);
            assertThatTheFullCostOfThePartyIs(party, 0);
        }
    }

    @Test
    public void anInvitedUsertCanNotConfirmAssistanceBecauseTheDateTimeLimit() {
        Party party = partyWithGuestsAndCostPerAssistance(pricePerAssistant);
        List<User> guests = party.guests();
        User user = guests.get(0);
        try {
            party.confirmAssistance(user.email(), eventFactory.invalidConfirmationDate());
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), EventValidator.ERROR_THE_CONFIRMATION_DATE_IS_AFTER_THE_INVITATION_LIMIT);
            assertThatTheFullCostOfThePartyIs(party, 0);
        }
    }

    @Test
    public void thePartyCanCalculateTheCostWithSuppliesAndConfirmations() {
        Party party = eventFactory.partyWithGuestsExpensesAndAPricePerAssistant(this.guests(), pricePerAssistant, eventFactory.expenses());
        List<User> guests = party.guests();
        User aUserThatIsInvited = guests.get(0);
        User anotherUserThatIsInvited = guests.get(1);

        party.confirmAssistance(aUserThatIsInvited.email(), confirmationDate);
        party.confirmAssistance(anotherUserThatIsInvited.email(), confirmationDate);

        assertThatTheFullCostOfThePartyIs(party, 600.00);
    }

    private Party partyWithGuestsAndCostPerAssistance(Double pricePerAssistant) {
        return createParty(guests(), pricePerAssistant);
    }

    private Party createParty(List<User> guests, Double pricePerAssistant) {
        return eventFactory.partyWithGuestsExpensesAndAPricePerAssistant(guests, pricePerAssistant, eventFactory.expenses());
    }

    private void assertThatTheFullCostOfThePartyIs(Party party, double costoTotalDeLaFiesta) {
        assertEquals(costoTotalDeLaFiesta, party.totalCost(), 0);
    }
}
