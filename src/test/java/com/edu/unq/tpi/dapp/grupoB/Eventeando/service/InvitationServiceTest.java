package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class InvitationServiceTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private InvitationService invitationService;

    private UserFactory userFactory;
    private EventFactory eventFactory;
    private User organizer;
    private List<Expense> expenses;
    private User guest;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
        organizer = savedUser();
        expenses = savedExpenses();
        guest = savedUser();
    }

    private List<Expense> savedExpenses() {
        return expenseDao.saveAll(eventFactory.expenses());
    }

    private User savedUser() {
        return userDao.save(userFactory.user());
    }

    @Test
    public void aUserWithNoInvitations() {

        List<Invitation> invitations = invitationService.userInvitations(organizer.id());
        assertThat(invitations).isEmpty();
    }

    @Test
    public void canObtainTheUserInvitations() {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(guest), organizer, expenses);
        eventDao.save(anEvent);
        invitationService.sendInvitations(anEvent);

        List<Invitation> invitations = invitationService.userInvitations(guest.id());

        assertThat(invitations).isEqualTo(guest.invitations());
    }

    @Test
    public void canObtainTheUserInvitationsFromAllEvents() {
        invitationService.sendInvitations(createEvent());
        invitationService.sendInvitations(createEvent());

        List<Invitation> invitations = invitationService.userInvitations(guest.id());

        assertThat(invitations.size()).isEqualTo(2);
        assertThat(invitations).isEqualTo(guest.invitations());
    }

    @Test
    public void canConfirmAnInvitation() {
        invitationService.sendInvitations(createEvent());

        Invitation invitation = guest.invitations().get(0);
        invitationService.confirmInvitation(invitation.id());

        assertTrue(invitation.isConfirmed());
    }

    @Test
    public void confirmedInvitationsDontAreRetrieved() {
        invitationService.sendInvitations(createEvent());
        Invitation invitation = guest.invitations().get(0);
        invitationService.confirmInvitation(invitation.id());

        List<Invitation> invitations = invitationService.userInvitations(guest.id());

        assertThat(invitations).isEmpty();
    }

    private Event createEvent() {
        Event anEvent = eventFactory.baquitaCrowfunding(organizer, Collections.singletonList(guest), expenses);
        return eventDao.save(anEvent);
    }

}
