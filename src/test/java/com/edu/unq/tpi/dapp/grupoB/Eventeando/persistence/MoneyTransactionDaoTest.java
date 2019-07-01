package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.YearMonth;
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

        Loan loan = Loan.create(user);
        moneyTransactionDao.save(loan);

        LoanPayment loanPayment = LoanPayment.create(user, loan);
        moneyTransactionDao.save(loanPayment);

        Extraction extraction = Extraction.create(user, LocalDate.now(), 100.00);
        moneyTransactionDao.save(extraction);

        DepositByCreditCard depositByCreditCard = DepositByCreditCard.create(user, LocalDate.of(2019, 7, 1), 100.00, YearMonth.of(2019, 10), "4242424242424242");
        moneyTransactionDao.save(depositByCreditCard);

        DepositByCash depositByCash = DepositByCash.create(user, LocalDate.now(), 500.00);
        moneyTransactionDao.save(depositByCash);

        List<MoneyTransaction> transactions = moneyTransactionDao.findAllByUser(user);
        assertThat(transactions).containsExactlyInAnyOrder(loan, loanPayment, extraction, depositByCreditCard, depositByCash);
    }
}
