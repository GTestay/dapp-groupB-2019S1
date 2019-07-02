package com.edu.unq.tpi.dapp.grupoB.Eventeando.task;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
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
    private AccountManager accountManager;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private Moneylender moneyLender;

    @Test
    public void forUsersWithALoanInProgressChargeTheLoan(){
        User user = userFactory.user();
        User anotherUser = userFactory.user();
        anotherUser.cashDeposit(500.00, accountManager);

        user.takeOutALoan(moneyLender, accountManager);

        service.execute(moneyLender, accountManager);

        assertEquals(800.00, user.balance(accountManager), 0);
        assertEquals(500.00, anotherUser.balance(accountManager), 0);
    }

    @Test
    public void forUsersWhoAlreadyPayedTheLoanDoNotDoAnything(){
        User user = userFactory.user();
        user.cashDeposit(600.00, accountManager);
        User anotherUser = userFactory.user();

        user.takeOutALoan(moneyLender, accountManager);
        anotherUser.takeOutALoan(moneyLender, accountManager);

        int i;
        for (i = 0; i < 6; i++) {
            user.payLoan(accountManager, moneyLender);
        }

        service.execute(moneyLender, accountManager);

        assertEquals(400, user.balance(accountManager), 0);
        assertEquals(800, anotherUser.balance(accountManager), 0);
    }
}
