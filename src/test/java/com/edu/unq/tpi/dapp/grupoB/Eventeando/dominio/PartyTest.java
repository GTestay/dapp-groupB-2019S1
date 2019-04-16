package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PartyTest {

    private String organizer;
    private LocalDateTime anInvitationLimitDate;
    private double precioPorAsistente;

    @Before
    public void setUp() {
        organizer = "Pepito";
        anInvitationLimitDate = LocalDateTime.now();
        precioPorAsistente = 100.0;
    }

    @Test
    public void aPartyCanBeCreatedWithAnOrganizerAListOfGuestsAndALimitConfirmationDate() {
        Party pepitoParty = partyWithGuests();

        assertEquals(organizer, pepitoParty.organizer());
        assertFalse(pepitoParty.guests().isEmpty());
        assertEquals(anInvitationLimitDate, pepitoParty.invitationLimitDate());
    }

    private HashMap<String, String> guests() {
        HashMap<String, String> guests = new HashMap<>();
        guests.put("email@gmail.com", "Juan");
        guests.put("pepita@gmail.com", "Pepita");
        return guests;
    }

    private Party partyWithGuests() {
        return new Party(organizer, guests(), anInvitationLimitDate, 0.0);
    }

    @Test
    public void aPartyCanNotBeCreatedWithoutGuests() {
        try {
            new Party(organizer, new HashMap<>(), anInvitationLimitDate, 0.0);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), Party.ERROR_CAN_NOT_BE_CREATED_WITHOUT_GUESTS);
        }

    }

    private void assertThatThePriceOfSuppliesOfAPartyIs(int price, Party party) {
        assertEquals(price, party.totalPriceOfSupplies(), 0);
    }

    @Test
    public void theSuppliesCostOfAPartyWithoutSuppliesIsZero() {
        assertThatThePriceOfSuppliesOfAPartyIs(0, partyWithGuests());
    }

    @Test
    public void aPartyCanNotAddASupplyWhoseCostIsNegative() {
        try {
            partyWithGuests().addSupply("Coca de 1 litro", -1.00);
            fail();
        } catch (RuntimeException e) {
            assertThatThePriceOfSuppliesOfAPartyIs(0, partyWithGuests());
        }
    }

    @Test
    public void aPartyCanAddASupply() {
        Party partyDePepito = partyWithGuests();

        partyDePepito.addSupply("Coca de 1 litro", 100.00);
        partyDePepito.addSupply("Sanguches de Miga x 24", 200.00);

        assertThatThePriceOfSuppliesOfAPartyIs(300, partyDePepito);
    }

    @Test
    public void theCostOfAPartyWithoutConfirmationsAndSuppliesIsOfZero() {
        assertThatTheFullCostOfThePartyIs(partyWithGuests(), 0);
    }

    @Test
    public void theCostOfThePartyWithoutSuppliesAndWithConfirmationsIsCalculatedByThePricePerAssistant() {
        Party unaPartyConUnaConfirmacion = partyWithGuestsAndCostPerAssistance(precioPorAsistente);

        unaPartyConUnaConfirmacion.confirmAssistance("email@gmail.com");

        assertThatTheFullCostOfThePartyIs(unaPartyConUnaConfirmacion, precioPorAsistente);
    }

    @Test
    public void theTotalCostOfAPartyWIthoutAssistantsAndWithSuppliesIsZero() {
        Party partyWithSupplies = partyWithGuests();

        partyWithSupplies.addSupply("Coca de 1 litro", 100.00);

        assertThatTheFullCostOfThePartyIs(partyWithSupplies, 0);
    }

    @Test
    public void aUsertThatIsNotInvitedCanNotConfirmAssistance() {
        Party party = partyWithGuests();
        try {
            party.confirmAssistance("unEmailQueNoEstaInvitado@gmail.com");
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), Party.ERROR_THE_USER_WAS_NOT_INVITED);
            assertThatTheFullCostOfThePartyIs(party, 0);
        }
    }

    @Test
    public void thePartyCanCalculateTheCostWithSuppliesAndConfirmations() {
        Party party = partyWithGuestsAndCostPerAssistance(precioPorAsistente);
        party.addSupply("Coca de 1 litro", 100.00);
        party.addSupply("Pizza", 100.00);
        party.confirmAssistance("email@gmail.com");
        party.confirmAssistance("pepita@gmail.com");

        assertThatTheFullCostOfThePartyIs(party, 600.00);
    }

    private Party partyWithGuestsAndCostPerAssistance(Double precioPorAsistente) {
        return createParty(guests(), precioPorAsistente);
    }

    private Party createParty(HashMap<String, String> invitados, Double precioPorAsistente) {
        return new Party(organizer, invitados, anInvitationLimitDate, precioPorAsistente);
    }

    private void assertThatTheFullCostOfThePartyIs(Party party, double costoTotalDeLaFiesta) {
        assertEquals(costoTotalDeLaFiesta, party.totalCost(), 0);
    }
}
