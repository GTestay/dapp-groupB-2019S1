package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    private UserFactory userFactory;
    private User organizer;


    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void anUserIsRetrieved() throws Exception {
        organizer = userFactory.user();
        userDao.save(organizer);


        Optional<User> user = userDao.findById(organizer.id());
        assertThat(user).hasValue(organizer);
    }
}