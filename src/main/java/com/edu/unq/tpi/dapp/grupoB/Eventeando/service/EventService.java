package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.ExpensesNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventDao eventDao;
    private final UserService userService;
    private final MailSenderService service;
    private final ExpenseDao expenseDao;

    @Autowired
    public EventService(UserService userService, EventDao eventDao, MailSenderService service, ExpenseDao expenseDao) {
        this.userService = userService;
        this.eventDao = eventDao;
        this.service = service;
        this.expenseDao = expenseDao;
    }

    public List<Event> allEvents() {
        return eventDao.findAll();
    }

    public Party createParty(String organizerEmail, String description, List<String> guestsEmails, LocalDateTime invitationLimitDate, List<Long> expensesId) {
        User organizer = userService.findUserByEmail(organizerEmail);
        List<User> guests = userService.obtainUsersFromEmails(guestsEmails);
        List<Expense> expenses = getExpenses(expensesId);
        return Party.create(organizer, description, guests, expenses, LocalDateTime.from(invitationLimitDate));
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
        sendInvitationsEmails(invitations);
    }

    private void sendInvitationsEmails(List<Invitation> invitations) {
        invitations.forEach(invitation -> sendEmail(invitation));

    }

    private void sendEmail(Invitation invitation) {
        try {
            service.sendEmail(invitation.guestEmail(), bodyDelMail(invitation), subjectDelMail(invitation));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String subjectDelMail(Invitation invitation) {
        return "Nueva Invitacion de " + invitation.organizerFullName();
    }

    private String bodyDelMail(Invitation invitation) {
        return "Hola! " + invitation.guestFullName() + ". Venite a mi evento! \n" +
                invitation.eventDescription();
    }

}
