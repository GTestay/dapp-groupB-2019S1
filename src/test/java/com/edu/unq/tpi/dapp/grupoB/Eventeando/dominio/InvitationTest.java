package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InvitationTest {


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

        Invitation invitation = new Invitation(party, guest);

        assertTrue(guest.invitations().contains(invitation));
        assertFalse(invitation.isConfirmed());
        assertFalse(party.guestHasConfirmed(guest));
    }

    @Test
    public void aUserInvitedToAnEventConfirmTheInvitation() {
        User guest = this.userFactory.user();
        Party party = eventFactory.partyWithGuests(Collections.singletonList(guest));

        Invitation invitation = new Invitation(party, guest);
        invitation.confirm(confirmationDate);

        assertTrue(invitation.isConfirmed());
        assertTrue(party.guestHasConfirmed(guest));
    }

    @Test
    public void aListOfInvitationIsCreatedWithTheUsersInvitedInAnEvent() {

        List<User> usersInvitedInEvent = this.userFactory.someUsers();
        Party party = eventFactory.partyWithGuests(usersInvitedInEvent);
        List<Invitation> invitations = Invitation.createListOfInvitationsWith(party);

        assertEquals(usersInvitedInEvent.size(), invitations.size());

        List<Invitation> allInvitations = usersInvitedInEvent.stream()
                .map(User::invitations).flatMap(Collection::stream).collect(Collectors.toList());

        assertTrue(allInvitations.containsAll(invitations));
    }


}
