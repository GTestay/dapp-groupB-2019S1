package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public abstract class ControllerTest {

    protected MockMvc clientRest;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() throws Exception {
        clientRest = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected <T> T mapResponse(MvcResult mvcResult, Class<T> valueType) throws java.io.IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), valueType);
    }

    protected ResultActions performPost(JSONObject jsonObject, String url) throws Exception {
        return clientRest.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonObject.toString()));
    }

    protected ResultActions performGet(String url) throws Exception {
        return clientRest.perform(get(url).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    protected MvcResult assertStatusIsOkAndMediaType(ResultActions perform) throws Exception {
        return perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
    }


    protected MvcResult assertStatusIsOk(ResultActions perform) throws Exception {
        return perform.andExpect(status().isOk()).andReturn();
    }

    protected String getBodyOfTheRequest(MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }

    protected MvcResult assertThatResponseIsCreated(ResultActions perform) throws Exception {
        return perform.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
    }

    protected MvcResult assertThatRequestIsNotFound(ResultActions perform) throws Exception {
        return perform.andExpect(status().isNotFound())
                .andReturn();
    }

    protected MvcResult assertThatRequestIsBadRequest(ResultActions perform) throws Exception {
        return perform.andExpect(status().isBadRequest())
                .andReturn();
    }
}