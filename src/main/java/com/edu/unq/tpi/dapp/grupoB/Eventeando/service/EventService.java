package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.ExpensesNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.InvitationDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.EventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventDao eventDao;
    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final ExpenseDao expenseDao;
    private final InvitationDao invitationDao;

    @Autowired
    public EventService(UserService userService, EventDao eventDao, MailSenderService mailSenderService, ExpenseDao expenseDao, InvitationDao invitationDao) {
        this.userService = userService;
        this.eventDao = eventDao;
        this.mailSenderService = mailSenderService;
        this.expenseDao = expenseDao;
        this.invitationDao = invitationDao;
    }

    public List<Event> allEvents() {
        return eventDao.findAll();
    }

    public Party createParty(EventData eventData, LocalDateTime invitationLimitDate) {
        Party party = new EventBuilder(eventData, this).buildParty(invitationLimitDate);
        sendInvitations(party);

        return party;
    }

    public PotluckEvent createPotluckEvent(EventData eventData) {
        PotluckEvent potluckEvent = new EventBuilder(eventData, this).buildPotluckEvent();
        sendInvitations(potluckEvent);

        return potluckEvent;
    }

    public BaquitaSharedExpensesEvent createBaquitaSharedExpensesEvent(EventData eventData) {
        BaquitaSharedExpensesEvent baquitaSharedExpensesEvent = new EventBuilder(eventData, this).buildBaquitaSharedExpensesEvent();
        sendInvitations(baquitaSharedExpensesEvent);

        return baquitaSharedExpensesEvent;
    }

    public BaquitaCrowdFundingEvent createBaquitaCrowdFundingEvent(EventData eventData) {
        BaquitaCrowdFundingEvent baquitaCrowdFundingEvent = new EventBuilder(eventData, this).buildBaquitaCrowdFundingEventEvent();
        sendInvitations(baquitaCrowdFundingEvent);

        return baquitaCrowdFundingEvent;
    }

    void sendInvitations(Event event) {
        List<Invitation> invitations = Invitation.createListOfInvitationsWith(event);
        invitationDao.saveAll(invitations);
        mailSenderService.sendInvitationsEmails(invitations);
    }

    List<Expense> getExpenses(List<Long> expensesId) {
        List<Expense> expenses = expenseDao.findAllById(expensesId);
        if (expenses.size() != expensesId.size()) {
            throw new ExpensesNotFound(messageExpensesNotFound());
        }
        return expenses;
    }

    public static String messageExpensesNotFound() {
        return "Invalid expenses Ids";
    }

    List<User> obtainUsersFromEmails(List<String> guestsEmails) {
        return userService.obtainUsersFromEmails(guestsEmails);
    }

    User findUserByEmail(String organizerEmail) {
        return userService.findUserByEmail(organizerEmail);
    }

    public List<Event> allEventsOf(Long id) {
        return eventDao.findAllByOrganizer_Id(id);
    }
}
