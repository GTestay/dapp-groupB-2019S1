package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InvitationsTest {


    private UserFactory userFactory;
    private EventFactory eventFactory;
    private LocalDateTime confirmationDate;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory(userFactory);
        confirmationDate = LocalDateTime.now();
    }

    @Test
    public void aNewUserHasNoInvitations() {
        assertTrue(userFactory.user().invitations().isEmpty());
    }

    @Test
    public void aUserIsInvitedToAnEvent() {
        User guest = this.userFactory.user();
        Party party = eventFactory.partyWithGuests(Collections.singletonList(guest));

        EventInvitation eventInvitation = new EventInvitation(party, guest);

        assertFalse(guest.invitations().isEmpty());
        assertFalse(eventInvitation.isConfirmed());
        assertFalse(party.guestHasConfirmed(guest));
    }

    @Test
    public void aUserInvitedToAnEventConfirmTheInvitation() {
        User guest = this.userFactory.user();
        Party party = eventFactory.partyWithGuests(Collections.singletonList(guest));

        EventInvitation eventInvitation = new EventInvitation(party, guest);
        eventInvitation.confirm(confirmationDate);

        assertTrue(eventInvitation.isConfirmed());
        assertTrue(party.guestHasConfirmed(guest));
    }


}
