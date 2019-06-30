package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MoneyTransactionDaoTest {

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private UserDao userDao;

    private UserFactory userFactory;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void moneyTransactionsOfAnUserAreRetrieved() {
        User user = userFactory.user();
        userDao.save(user);

        Loan transaction = Loan.create(user);
        moneyTransactionDao.save(transaction);

        LoanPayment anotherTransaction = LoanPayment.create(user, transaction);
        moneyTransactionDao.save(anotherTransaction);

        List<MoneyTransaction> transactions = moneyTransactionDao.findAllByUser(user);
        assertThat(transactions).containsExactlyInAnyOrder(transaction, anotherTransaction);
    }
}
