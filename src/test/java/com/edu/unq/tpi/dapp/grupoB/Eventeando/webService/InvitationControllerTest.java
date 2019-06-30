package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.InvitationDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InvitationControllerTest extends ControllerTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private ExpenseDao expenseDao;
    @Autowired
    private InvitationDao invitationDao;

    private UserFactory userFactory;
    private EventFactory eventFactory;
    private User organizer;
    private User guest;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
        organizer = savedUser();
        guest = savedUser();
    }

    @Test
    public void aUserWithNoInvitations() throws Exception {

        ResultActions perform = clientRest.perform(get(url(organizer.id())));

        assertStatusIsOkAndMediaType(perform);
    }

    @Test
    public void canNotObtainInvitationsFromAnInexistentUser() throws Exception {

        ResultActions perform = clientRest.perform(get(url(-1L)));

        assertThatRequestIsNotFound(perform);
    }

    @Test
    public void canObtainTheUserInvitations() throws Exception {
        createEvent();
        Invitation guestInvitation = guest.invitations().get(0);

        ResultActions perform = clientRest.perform(get(url(guest.id())));

        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);
        String bodyOfTheRequest = getBodyOfTheRequest(mvcResult);
        Invitation responseInvitation = deserializeInvitations(bodyOfTheRequest).get(0);

        assertThat(responseInvitation.id()).isEqualTo(guestInvitation.id());
    }

    private List<Invitation> deserializeInvitations(String bodyOfTheRequest) throws java.io.IOException {
        return Arrays.asList(objectMapper.readValue(bodyOfTheRequest, Invitation[].class));
    }

    @Test
    public void canConfirmAnInvitation() throws Exception {
        createEvent();

        Invitation invitation = guest.invitations().get(0);
        String url = "/invitations/" + invitation.id() + "/confirm";
        ResultActions perform = performGet(url);

        perform.andExpect(status().isOk());
    }

    @Test
    public void canNotConfirmAnInvitationWhenItDontExist() throws Exception {
        String url = "/invitations/" + -1 + "/confirm";
        ResultActions perform = performGet(url);

        perform.andExpect(status().isNotFound());
    }

    private Event createEvent() {
        Event anEvent = eventFactory.baquitaCrowfunding(organizer, Collections.singletonList(guest), savedExpenses());
        eventDao.save(anEvent);
        invitationDao.saveAll(Invitation.createListOfInvitationsWith(anEvent));
        return anEvent;
    }

    private String url(Long id) {
        return "/users/" + id + "/invitations";
    }

    private User savedUser() {
        User user = userFactory.user();
        userDao.save(user);
        return user;
    }

    public List<Expense> savedExpenses() {
        List<Expense> expenses = eventFactory.expenses();
        expenseDao.saveAll(expenses);
        return expenses;
    }
}
