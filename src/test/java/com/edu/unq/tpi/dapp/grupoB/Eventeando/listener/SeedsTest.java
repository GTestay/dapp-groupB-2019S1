package com.edu.unq.tpi.dapp.grupoB.Eventeando.listener;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class SeedsTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void seedApplicationWithUsers(){
        List<User> users = userDao.findAll();

        assertFalse(users.isEmpty());

        User user = users.get(0);

        assertNotNull(user.name());
        assertNotNull(user.lastname());
        assertNotNull(user.email());
        assertNotNull(user.password());
        assertNotNull(user.birthday());
    }
}
