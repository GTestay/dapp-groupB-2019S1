package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.MoneyTransaction;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.UserException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService.messageUserNotFound;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MoneyTransactionDao moneyTransactionDao;
    @Autowired
    private AccountManagerService accountManagerService;
    @Autowired
    private UserService userService;

    private UserFactory userFactory;


    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void aUserIsCreated() {
        User newUserToCreate = userFactory.user();

        User createdUser = userService.createUser(newUserToCreate);

        assertThat(createdUser).isEqualTo(newUserToCreate);
    }

    @Test
    public void canNotCreateAnUserIfTheEmailWasTaken() {
        User newUserToCreate = userFactory.user();
        userService.createUser(newUserToCreate);
        try {
            userService.createUser(newUserToCreate);
            fail();
        } catch (UserException e) {
            assertThat(e.getMessage()).isEqualTo(UserValidator.USER_EMAIL_IS_ALREADY_TAKEN);
        }
    }


    @Test
    public void aSavedUserIsRetrieved() {
        User newUserToCreate = userFactory.user();
        userService.createUser(newUserToCreate);
        User searchedUser = userService.searchUser(newUserToCreate.id());

        assertThat(searchedUser).isEqualTo(newUserToCreate);
    }


    @Test
    public void aSavedUserIsRetrievedByEmail() {
        User newUserToCreate = userFactory.user();
        userService.createUser(newUserToCreate);
        User searchedUser = userService.findUserByEmail(newUserToCreate.email());

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

    @Test
    public void whenThereAreNoUsersNoneEmailIsRetrieved() {
        assertThat(userService.allEmailsContaining("")).isEmpty();
    }

    @Test
    public void canBringAllUsersEmailsRegistered() {
        User newUser = userFactory.user();
        User otherUser = userFactory.user();
        User createdUser1 = userService.createUser(newUser);
        User createdUser2 = userService.createUser(otherUser);


        List<String> emails = userService.allEmailsContaining("");
        assertThat(emails).isNotEmpty();
        assertThat(emails).containsExactlyInAnyOrder(createdUser1.email(), createdUser2.email());
    }

    @Test
    public void canBringAllUserMoneyTransactions() {
        User user = userFactory.userWithCash(1000, accountManagerService);
        userDao.save(user);
        List<MoneyTransaction> transactions = accountManagerService.transactions(user);

        List<MoneyTransaction> moneyTransactions = userService.allMoneyTransactionsOf(user.id());

        assertThat(moneyTransactions).isNotEmpty();
        assertThat(moneyTransactions).isEqualTo(transactions);
    }

}