package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventeandoNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.ExpensesNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ScoreDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.EventValidator;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.EventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventDao eventDao;
    private final ScoreDao scoreDao;
    private final UserService userService;
    private final ExpenseDao expenseDao;
    private final InvitationService invitationService;

    @Autowired
    public EventService(UserService userService, EventDao eventDao, ScoreDao scoreDao, ExpenseDao expenseDao, InvitationService invitationService) {
        this.userService = userService;
        this.eventDao = eventDao;
        this.scoreDao = scoreDao;
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

    public void scoreAnEvent(Long eventId, Long userId, Integer rank) {
        Event event = findEvent(eventId);
        User user = userService.searchUser(userId);
        validateEventIsNotRatedByOrganizer(event, user);

        Score score = rankEvent(event, user, rank);
        scoreDao.save(score);
    }

    private Score rankEvent(Event event, User user, Integer rank) {
        Optional<Score> optionalScore = scoreDao.findAllByEvent_IdAndAndUser_Id(event.id(), user.id());
        optionalScore.ifPresent(score -> score.changeRank(rank));
        return optionalScore.orElseGet(() -> Score.create(event, user, rank));
    }

    public Integer eventScore(Long eventId) {
        return scoreDao.eventScore(findEvent(eventId).id()).orElse(0);
    }

    private void validateEventIsNotRatedByOrganizer(Event event, User user) {
        if (event.hasSameOrganizer(user))
            throw organizerCanNotRateEvent();
    }

    private RuntimeException organizerCanNotRateEvent() {
        return new RuntimeException(EventValidator.EVENT_CAN_NOT_BE_SCORED_BY_ORGANIZER);
    }

    private Event findEvent(Long eventId) {
        return this.eventDao.findById(eventId).orElseThrow(this::eventNotFound);
    }

    private EventeandoNotFound eventNotFound() {
        return new EventeandoNotFound(EventValidator.EVENT_NOT_FOUND);
    }

}
