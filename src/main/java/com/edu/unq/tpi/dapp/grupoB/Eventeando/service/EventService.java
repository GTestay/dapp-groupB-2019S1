package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.ExpensesNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.EventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventDao eventDao;
    private final UserService userService;
    private final ExpenseDao expenseDao;
    private final InvitationService invitationService;

    @Autowired
    public EventService(UserService userService, EventDao eventDao, ExpenseDao expenseDao, InvitationService invitationService) {
        this.userService = userService;
        this.eventDao = eventDao;
        this.expenseDao = expenseDao;
        this.invitationService = invitationService;
    }

    public List<Event> allEvents() {
        return eventDao.findAll();
    }

    public Party createParty(EventData eventData, LocalDateTime invitationLimitDate) {
        Party party = new EventBuilderService(eventData, this).buildParty(invitationLimitDate);
        confirmEvent(party);

        return party;
    }

    public PotluckEvent createPotluckEvent(EventData eventData) {
        PotluckEvent potluckEvent = new EventBuilderService(eventData, this).buildPotluckEvent();
        confirmEvent(potluckEvent);

        return potluckEvent;
    }

    public BaquitaSharedExpensesEvent createBaquitaSharedExpensesEvent(EventData eventData) {
        BaquitaSharedExpensesEvent baquitaSharedExpensesEvent = new EventBuilderService(eventData, this).buildBaquitaSharedExpensesEvent();
        confirmEvent(baquitaSharedExpensesEvent);

        return baquitaSharedExpensesEvent;
    }

    public BaquitaCrowdFundingEvent createBaquitaCrowdFundingEvent(EventData eventData) {
        BaquitaCrowdFundingEvent baquitaCrowdFundingEvent = new EventBuilderService(eventData, this).buildBaquitaCrowdFundingEventEvent();

        confirmEvent(baquitaCrowdFundingEvent);

        return baquitaCrowdFundingEvent;
    }

    void confirmEvent(Event event) {
        this.eventDao.save(event);
        invitationService.sendInvitations(event);
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
