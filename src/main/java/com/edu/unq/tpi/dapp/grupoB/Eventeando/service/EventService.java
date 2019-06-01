package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.ExpensesNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.InvitationDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.MailSenderService;
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

    public Party createParty(String organizerEmail, String description, List<String> guestsEmails, LocalDateTime invitationLimitDate, List<Long> expensesId) {
        User organizer = userService.findUserByEmail(organizerEmail);
        List<User> guests = userService.obtainUsersFromEmails(guestsEmails);
        List<Expense> expenses = getExpenses(expensesId);
        Party party = Party.create(organizer, description, guests, expenses, LocalDateTime.from(invitationLimitDate));
        createEvent(party);
        return party;
    }

    private List<Expense> getExpenses(List<Long> expensesId) {
        List<Expense> expenses = expenseDao.findAllById(expensesId);
        if (expenses.size() != expensesId.size()) {
            throw new ExpensesNotFound(messageExpensesNotFound());
        }
        return expenses;
    }

    public static String messageExpensesNotFound() {
        return "Invalid expenses Ids";
    }

    public void createEvent(Event event) {
        List<Invitation> invitations = Invitation.createListOfInvitationsWith(event);
        invitationDao.saveAll(invitations);
        mailSenderService.sendInvitationsEmails(invitations);
    }

}
