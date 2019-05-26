package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest extends ControllerTest {

    private User organizer;
    private UserFactory userFactory;
    private EventFactory eventFactory;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userFactory = new UserFactory();
        eventFactory = new EventFactory();

        organizer = userFactory.user();
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
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(organizer), organizer);
        eventDao.save(anEvent);

        ResultActions perform = getAllEvents();

        MvcResult mvcResult = assertStatusAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        Party eventRetrievedFromApi = parseJson(jsonObject, Party.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    @Test
    public void potLucksAreRetrieved() throws Exception {
        Event anEvent = eventFactory.potluckWithGuests(userFactory.someUsers(), this.organizer);
        eventDao.save(anEvent);

        ResultActions perform = getAllEvents();
        MvcResult mvcResult = assertStatusAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        PotluckEvent eventRetrievedFromApi = parseJson(jsonObject, PotluckEvent.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    @Test
    public void baquitasSharedExpensesAreRetrieved() throws Exception {
        Event anEvent = eventFactory.baquitaSharedExpenses(organizer, userFactory.someUsers());
        eventDao.save(anEvent);

        ResultActions perform = getAllEvents();
        MvcResult mvcResult = assertStatusAndMediaType(perform);

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
        Event anEvent = eventFactory.baquitaCrowfunding(organizer, userFactory.someUsers());
        eventDao.save(anEvent);
        ResultActions perform = getAllEvents();

        MvcResult mvcResult = assertStatusAndMediaType(perform);

        String eventsRequest = getBodyOfTheRequest(mvcResult);

        JSONArray actual = assertThatHasBroughtOneEvent(eventsRequest);

        JSONObject jsonObject = actual.getJSONObject(0);
        BaquitaCrowdFundingEvent eventRetrievedFromApi = parseJson(jsonObject, BaquitaCrowdFundingEvent.class);

        assertThatIsAValidEvent(anEvent, eventRetrievedFromApi);
    }

    private void assertThatIsAValidEvent(Event anEvent, Event eventRetrievedFromApi) {
        Assertions.assertThat(eventRetrievedFromApi.description()).isEqualTo(anEvent.description());
        Assertions.assertThat(eventRetrievedFromApi.totalCost()).isEqualTo(anEvent.totalCost());
        assertTrue(eventRetrievedFromApi.hasSameOrganizer(anEvent.organizer()));
    }

    private MvcResult assertStatusAndMediaType(ResultActions perform) throws Exception {
        return perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
    }

    private JSONArray assertThatHasBroughtOneEvent(String eventsRequest) throws JSONException {
        JSONArray actual = new JSONArray(eventsRequest);
        Assertions.assertThat(actual.length()).isEqualTo(1);
        return actual;
    }

    private ResultActions getAllEvents() throws Exception {
        return clientRest.perform(get(ulrAllEvents()));
    }

    private String getBodyOfTheRequest(MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }

    private String ulrAllEvents() {
        return "/events";
    }
}
