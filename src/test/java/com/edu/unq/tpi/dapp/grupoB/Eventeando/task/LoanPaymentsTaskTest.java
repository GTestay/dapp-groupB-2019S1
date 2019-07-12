package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class LoanPaymentsTaskTest {

    private UserFactory userFactory = new UserFactory();
    private LoanPaymentsTask service = new LoanPaymentsTask();

    @Autowired
    private AccountManagerService accountManagerService;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private MoneylenderService moneyLender;

    @Autowired
    private UserDao userDao;

    @Test
    public void forUsersWithALoanInProgressChargeTheLoan(){
        User user = getUser();
        User anotherUser = getUser();
        anotherUser.cashDeposit(500.00, accountManagerService);

        user.takeOutALoan(moneyLender, accountManagerService);

        service.execute(moneyLender, accountManagerService);

        assertEquals(800.00, user.balance(accountManagerService), 0);
        assertEquals(500.00, anotherUser.balance(accountManagerService), 0);
    }

    @Test
    public void forUsersWhoAlreadyPayedTheLoanDoNotDoAnything(){
        User user = getUser();
        user.cashDeposit(600.00, accountManagerService);
        User anotherUser = getUser();

        user.takeOutALoan(moneyLender, accountManagerService);
        anotherUser.takeOutALoan(moneyLender, accountManagerService);

        int i;
        for (i = 0; i < 6; i++) {
            user.payLoan(moneyLender, accountManagerService);
        }

        service.execute(moneyLender, accountManagerService);

        assertEquals(400, user.balance(accountManagerService), 0);
        assertEquals(800, anotherUser.balance(accountManagerService), 0);
    }

    protected User getUser() {
        User newUser = userFactory.user();

        userDao.save(newUser);

        return newUser;
    }
}
