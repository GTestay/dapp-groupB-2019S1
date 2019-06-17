package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.UserValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ControllerTest {

    @Autowired
    private UserDao userDao;

    private UserFactory userFactory;
    private User newUser;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userFactory = new UserFactory();
        newUser = userFactory.user();
    }

    @Test
    public void anUserIsCreated() throws Exception {
        JSONObject bodyRequest = getUserBody(newUser);

        ResultActions perform = performPost(bodyRequest, url());

        MvcResult result = assertThatResponseIsCreated(perform);

        String responseString = getBodyOfTheRequest(result);
        JSONObject jsonResponse = new JSONObject(responseString);
        jsonResponse.remove("id");
        JSONAssert.assertEquals(jsonResponse, bodyRequest, true);
    }

    @Test
    public void whenThereAreNoUsersNoneEmailIsRetrieved() throws Exception {
        ResultActions perform = getAllUsersEmails();

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
    }

    @Test
    public void canBringAllUsersEmailsRegistered() throws Exception {
        List<User> users = userFactory.someUsers();
        userDao.saveAll(users);
        ResultActions perform = getAllUsersEmails();

        MvcResult result = assertStatusIsOkAndMediaType(perform);

        String responseString = getBodyOfTheRequest(result);
        JSONArray jsonResponse = new JSONArray(responseString);

        assertThat(jsonResponse.length()).isEqualTo(users.size());
    }

    @Test
    public void canBringAllUsersEmailsRegisteredByFiltering() throws Exception {
        List<User> users = userFactory.someUsers();
        userDao.saveAll(users);
        String email = users.get(0).email();
        ResultActions perform = getUsers(url() + "/emails?email=" + email);

        MvcResult result = assertStatusIsOkAndMediaType(perform);

        String responseString = getBodyOfTheRequest(result);
        JSONArray jsonResponse = new JSONArray(responseString);

        assertThat(jsonResponse.length()).isEqualTo(1);
        assertThat(jsonResponse.get(0)).isEqualTo(email);

    }

    public ResultActions getAllUsersEmails() throws Exception {
        return getUsers(url() + "/emails");
    }

    public ResultActions getUsers(String url) throws Exception {
        return clientRest.perform(get(url).contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void anUserCanNotBeCreatedBecauseTheRequestIsInvalid() throws Exception {

        JSONObject bodyRequest = getUserBody(newUser);
        bodyRequest.put("email", "1");
        ResultActions perform = performPost(bodyRequest, url());

        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                .andReturn();
        assertThat(mvcResult.getResolvedException()).hasMessageContaining(UserValidator.USER_EMAIL_IS_INVALID);
    }


    @Test
    public void anUserIsRetrieved() throws Exception {

        User userPersisted = userDao.save(newUser);

        String urlTemplate = url() + "/" + userPersisted.id();
        ResultActions perform = getUserWithId(urlTemplate);

        MvcResult mvcResult = assertStatusIsOkAndMediaType(perform);
        String contentAsString = getBodyOfTheRequest(mvcResult);

        assertThat(objectMapper.writeValueAsString(userPersisted))
                .isEqualToIgnoringWhitespace(contentAsString);

    }

    public ResultActions getUserWithId(String urlTemplate) throws Exception {
        return getUsers(urlTemplate);
    }

    @Test
    public void whenTheUserIdDoesNotExistTheResponseIsNotFound() throws Exception {

        String urlTemplate = url() + "/" + -1;
        ResultActions perform = getUserWithId(urlTemplate);

        MvcResult mvcResult = assertThatRequestIsNotFound(perform);

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
