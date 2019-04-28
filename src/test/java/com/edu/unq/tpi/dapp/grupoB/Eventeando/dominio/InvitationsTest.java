package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InvitationsTest {


    private UserFactory userFactory;
    private EventFactory eventFactory;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory(userFactory);
    }

    @Test
    public void aNewUserHasNoInvitations() {
        assertTrue(userFactory.user().invitations().isEmpty());
    }

    @Test
    public void aUserIsInvitedToAPartyEvent() {
        User guest = this.userFactory.user();
        Party party = eventFactory.partyWithGuests(Collections.singletonList(guest));

        PartyInvitation partyInvitation = new PartyInvitation(party, guest);

        assertFalse(guest.invitations().isEmpty());
        assertFalse(partyInvitation.isConfirmed());
        assertFalse(party.guestHasConfirmed(guest));
    }

    @Test
    public void aUserInvitedToAPartyEventConfirmTheInvitation() {
        User guest = this.userFactory.user();
        Party party = eventFactory.partyWithGuests(Collections.singletonList(guest));

        PartyInvitation partyInvitation = new PartyInvitation(party, guest);
        partyInvitation.confirm();

        assertTrue(partyInvitation.isConfirmed());
        assertTrue(party.guestHasConfirmed(guest));
    }


}
