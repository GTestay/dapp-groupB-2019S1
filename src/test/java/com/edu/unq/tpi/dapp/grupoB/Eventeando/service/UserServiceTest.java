package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService.messageUserNotFound;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    public UserDao userDao;

    private UserService userService;
    private UserFactory userFactory;


    @Before
    public void setUp() throws Exception {
        userFactory = new UserFactory();
        userService = new UserService(userDao);
    }

    @Test
    public void aUserIsCreated() throws Exception {
        User newUserToCreate = userFactory.user();

        User createdUser = userService.createUser(newUserToCreate);

        assertThat(createdUser).isEqualTo(newUserToCreate);
    }

    @Test
    public void aSavedUserIsRetrieved() throws Exception {
        User newUserToCreate = userFactory.user();
        userService.createUser(newUserToCreate);
        User searchedUser = userService.searchUser(newUserToCreate.id());

        assertThat(searchedUser).isEqualTo(newUserToCreate);
    }


    @Test
    public void whenCouldNotFindAUserAnExceptionIsThrow() throws Exception {
        try {
            userService.searchUser((long) -1);
            fail();
        } catch (RuntimeException e) {
            assertThat(e).hasMessage(messageUserNotFound());
        }

    }
}