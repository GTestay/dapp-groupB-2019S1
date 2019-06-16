package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.ExpenseDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.EventService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest extends ControllerTest {

    private User organizer;
    private UserFactory userFactory;
    private EventFactory eventFactory;

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;
    private List<User> guests;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userFactory = new UserFactory();
        eventFactory = new EventFactory();

        organizer = savedUser();
        guests = savedGuests();
    }

    public List<User> savedGuests() {
        List<User> users = userFactory.someUsers();
        userDao.saveAll(users);
        return users;
    }

    public User savedUser() {
        User user = userFactory.user();
        userDao.save(user);
        return user;
    }

    @Test
    public void noneEventIsRetrieved() throws Exception {

        ResultActions perform = getAllEvents();

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
    }

    @Test
    public void partiesAreRetrieved() throws Exception {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(organizer), organizer, savedExpenses());
        eventDao.save(anEvent);

        ResultActions perform = getAllEvents();

        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        Party eventRetrievedFromApi = parseJson(jsonObject, Party.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    @Test
    public void potLucksAreRetrieved() throws Exception {
        Event anEvent = eventFactory.potluckWithGuests(savedGuests(), this.organizer, savedExpenses());
        eventDao.save(anEvent);

        ResultActions perform = getAllEvents();
        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        PotluckEvent eventRetrievedFromApi = parseJson(jsonObject, PotluckEvent.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    @Test
    public void baquitasSharedExpensesAreRetrieved() throws Exception {
        Event anEvent = eventFactory.baquitaSharedExpenses(organizer, savedGuests(), savedExpenses());
        eventDao.save(anEvent);

        ResultActions perform = getAllEvents();
        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);

        BaquitaSharedExpensesEvent eventRetrievedFromApi = parseJson(jsonObject, BaquitaSharedExpensesEvent.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    private <T> T parseJson(JSONObject jsonObject, Class<T> valueType) throws java.io.IOException {
        return objectMapper.readValue(jsonObject.toString(), valueType);
    }

    @Test
    public void baquitasCrowfundingAreRetrieved() throws Exception {
        Event anEvent = eventFactory.baquitaCrowfunding(organizer, savedGuests(), savedExpenses());
        eventDao.save(anEvent);
        ResultActions perform = getAllEvents();

        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        BaquitaCrowdFundingEvent eventRetrievedFromApi = parseJson(jsonObject, BaquitaCrowdFundingEvent.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }


    @Test
    public void canNotCreateAnEventBecauseTheGivenOrganizerEmailDontExist() throws Exception {

        Party anEvent = eventFactory.partyWithGuests(guests, userFactory.user(), savedExpenses());
        expenseDao.saveAll(anEvent.expenses());

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatRequestIsNotFound(perform);

        assertThat(mvcResult.getResolvedException())
                .hasMessageContaining(UserService.messageUserNotFound());
    }

    @Test
    public void canNotCreateAnEventBecauseTheGivenGuestsEmailsDontExists() throws Exception {

        Party anEvent = eventFactory.partyWithGuests(userFactory.someUsers(), organizer, savedExpenses());
        expenseDao.saveAll(anEvent.expenses());

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatRequestIsNotFound(perform);

        assertThat(mvcResult.getResolvedException())
                .hasMessageContaining(UserService.usersNotFoundFromEmails());
    }

    @Test
    public void canNotCreateAnEventBecauseTheGivenExpensesDontExists() throws Exception {
        userDao.saveAll(guests);
        userDao.save(organizer);
        Party anEvent = eventFactory.partyWithGuests(guests, organizer, eventFactory.expenses());
        anEvent.expenses().forEach(expense -> expense.setId(0L));

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatRequestIsNotFound(perform);

        assertThat(mvcResult.getResolvedException())
                .hasMessageContaining(EventService.messageExpensesNotFound());
    }

    @Test
    public void canCreateAPartyEvent() throws Exception {
        Party anEvent = eventFactory.partyWithGuests(guests, organizer, savedExpenses());
        persistExpensesAndUsers(anEvent);

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatResponseIsCreated(perform);
        Party newPartyCreated = mapResponse(mvcResult, Party.class);
        assertThatIsAValidEvent(anEvent, newPartyCreated);
    }

    @Test
    public void canCreateAPotluckEvent() throws Exception {
        PotluckEvent anEvent = eventFactory.potluckWithGuests(guests, organizer, savedExpenses());
        persistExpensesAndUsers(anEvent);

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatResponseIsCreated(perform);

        PotluckEvent potluckEvent = mapResponse(mvcResult, PotluckEvent.class);

        assertThatIsAValidEvent(anEvent, potluckEvent);
    }

    public void persistExpensesAndUsers(Event anEvent) {
        userDao.saveAll(guests);
        userDao.save(organizer);
        expenseDao.saveAll(anEvent.expenses());
    }

    @Test
    public void canCreateABaquitaSharedExpensesEvent() throws Exception {
        BaquitaSharedExpensesEvent anEvent = eventFactory.baquitaSharedExpenses(organizer, guests, savedExpenses());
        persistExpensesAndUsers(anEvent);

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatResponseIsCreated(perform);

        BaquitaSharedExpensesEvent baquitaSharedExpensesEvent = mapResponse(mvcResult, BaquitaSharedExpensesEvent.class);

        assertThatIsAValidEvent(anEvent, baquitaSharedExpensesEvent);
    }

    @Test
    public void canCreateABaquitaCrowfundingEvent() throws Exception {

        BaquitaCrowdFundingEvent anEvent = eventFactory.baquitaCrowfunding(organizer, guests, savedExpenses());
        persistExpensesAndUsers(anEvent);

        JSONObject jsonObject = eventToJson(anEvent);

        ResultActions perform = performPost(jsonObject, url());

        MvcResult mvcResult = assertThatResponseIsCreated(perform);

        BaquitaCrowdFundingEvent baquitaCrowdFundingEvent = mapResponse(mvcResult, BaquitaCrowdFundingEvent.class);

        assertThatIsAValidEvent(anEvent, baquitaCrowdFundingEvent);
    }


    @Test
    public void aBadRequestIsGivenWhenSendingAnInvalidEventType() throws Exception {
        BaquitaCrowdFundingEvent anEvent = eventFactory.baquitaCrowfunding(organizer, guests, savedExpenses());

        JSONObject jsonObject = eventToJson(anEvent);
        jsonObject.put("type", "InvalidType");

        ResultActions perform = performPost(jsonObject, url());
        perform.andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void givenAnUserIdAllEventOfTheUserAreRetrieved() throws Exception {
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(organizer), organizer, savedExpenses());
        eventDao.save(anEvent);

        ResultActions perform = getAllEventsFrom(organizer.id());

        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        Party eventRetrievedFromApi = parseJson(jsonObject, Party.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    private ResultActions getAllEventsFrom(Long userId) throws Exception {
        return clientRest.perform(get(url().concat("/" + userId)));
    }


    private JSONObject eventToJson(Event anEvent) throws JSONException {
        return createJson(anEvent.getClass().getSimpleName(), anEvent.description(), anEvent.organizer(), anEvent.guests(), anEvent.expenses());
    }

    private JSONObject eventToJson(Party anEvent) throws JSONException {
        JSONObject jsonObject = createJson(anEvent.getClass().getSimpleName(), anEvent.description(), anEvent.organizer(), anEvent.guests(), anEvent.expenses());
        jsonObject.put("invitationLimitDate", anEvent.invitationLimitDate());
        return jsonObject;
    }

    private JSONObject createJson(String simpleName, String description, User organizer, List<User> guests, List<Expense> expenses) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", simpleName);
        jsonObject.put("description", description);
        jsonObject.put("organizerEmail", organizer.email());
        jsonObject.put("guestsEmails", new JSONArray(guests.stream().map(User::email).collect(Collectors.toList())));
        jsonObject.put("expensesIds", new JSONArray(expenses.stream().map(Expense::id).collect(Collectors.toList())));
        return jsonObject;
    }

    private void assertThatIsAValidEvent(Event anEvent, Event eventRetrievedFromApi) {
        Assertions.assertThat(eventRetrievedFromApi.description()).isEqualTo(anEvent.description());
        Assertions.assertThat(eventRetrievedFromApi.totalCost()).isEqualTo(anEvent.totalCost());
        assertTrue(eventRetrievedFromApi.hasSameOrganizer(anEvent.organizer()));
    }

    private JSONArray assertThatHasBroughtOneEvent(String eventsRequest) throws JSONException {
        JSONArray actual = new JSONArray(eventsRequest);
        Assertions.assertThat(actual.length()).isEqualTo(1);
        return actual;
    }

    private ResultActions getAllEvents() throws Exception {
        return clientRest.perform(get(url()));
    }

    private String url() {
        return "/events";
    }

    public List<Expense> savedExpenses() {
        List<Expense> expenses = eventFactory.expenses();
        expenseDao.saveAll(expenses);
        return expenses;
    }
}
