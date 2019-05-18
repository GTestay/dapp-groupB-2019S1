package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.EventDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc clientRest;
    private UserFactory userFactory;
    private User organizer;
    private EventFactory eventFactory;
    @MockBean
    private EventDao eventDao;

    @Before
    public void setUp() throws Exception {
        userFactory = new UserFactory();
        eventFactory = new EventFactory();
    }

    @Test
    public void noneEventIsRetrieved() throws Exception {

        ResultActions perform = clientRest.perform(get(ulrAllEvents()));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
    }

    @Test
    public void anEventIsRetrieved() throws Exception {
        organizer = userFactory.user();
        Event anEvent = eventFactory.partyWithGuests(Collections.singletonList(userFactory.user()), organizer);

        given(this.eventDao.findAll()).willReturn(Collections.singletonList(anEvent));

        ResultActions perform = clientRest.perform(get(ulrAllEvents()));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }


    private String ulrAllEvents() {
        return "/events";
    }


}
