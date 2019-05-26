package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ControllerTest {

    @Autowired
    UserDao userDao;

    @Autowired
    private ObjectMapper objectMapper;
    private UserFactory userFactory;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userFactory = new UserFactory();
    }

    @Test
    public void anUserIsCreated() throws Exception {
        User user = userFactory.user();
        JSONObject bodyRequest = getUserBody(user);

        ResultActions perform = clientRest.perform(post(url())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyRequest.toString()));

        MvcResult result = perform
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(responseString);
        jsonResponse.remove("id");
        JSONAssert.assertEquals(jsonResponse, bodyRequest, true);
    }

    @Test
    public void anUserCanNotBeCreatedBecauseTheRequestIsInvalid() throws Exception {


        User user = userFactory.user();

        JSONObject bodyRequest = getUserBody(user);
        bodyRequest.put("email", "1");
        ResultActions perform = clientRest.perform(post(url()).contentType(MediaType.APPLICATION_JSON).content(bodyRequest.toString()));

        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).hasMessageContaining(UserValidator.USER_EMAIL_IS_INVALID);
    }


    @Test
    public void anUserIsRetrieved() throws Exception {

        User userPersisted = userDao.save(userFactory.user());

        String urlTemplate = url() + "/" + userPersisted.id();
        ResultActions perform = clientRest
                .perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(userPersisted))
                .isEqualToIgnoringWhitespace(contentAsString);

    }

    @Test
    public void whenTheUserIdDoesNotExistTheResponseIsNotFound() throws Exception {

        String urlTemplate = url() + "/" + -1;
        ResultActions perform = clientRest.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = perform.andExpect(status().isNotFound())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .hasMessageContaining(UserService.messageUserNotFound());
    }

    private JSONObject getUserBody(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("birthday", user.birthday().toString());
        jsonObject.put("password", user.password());
        jsonObject.put("email", user.email());
        jsonObject.put("lastname", user.lastname());
        jsonObject.put("name", user.name());
        jsonObject.put("id", user.id());
        return jsonObject;
    }


    private String url() {
        return "/users";
    }


}
