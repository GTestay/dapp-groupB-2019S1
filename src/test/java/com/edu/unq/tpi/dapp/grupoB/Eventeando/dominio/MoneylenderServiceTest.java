package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MoneylenderServiceTest {

    private UserFactory userFactory;

    @Autowired
    private AccountManagerService accountManagerService;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private MoneylenderService moneyLender;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void getActualLoansForAllTheUsers() {
        User arya = getUser();
        User sansa = getUser();

        arya.takeOutALoan(moneyLender, accountManagerService);
        sansa.takeOutALoan(moneyLender, accountManagerService);

        assertHasLoanFrom(moneyLender, arya);
        assertHasLoanFrom(moneyLender, sansa);
    }

    private void assertHasLoanFrom(MoneylenderService moneylenderService, User user) { assertTrue(containLoan((ArrayList<Loan>) moneylenderService.actualLoans(), user)); }

    private boolean containLoan(ArrayList<Loan> actualLoans, User owner) { return actualLoans.stream().anyMatch(loan -> loan.isOwner(owner)); }

    @Test
    public void loanInformationOffAnUserWithFiveRemainingPayments() {
        User user = getUser();

        user.takeOutALoan(moneyLender, accountManagerService);

        user.payLoan(moneyLender, accountManagerService);

        assertEquals(moneyLender.remainingPayments(user, accountManagerService), 5);
        assertEquals(moneyLender.unpaidPayments(user), 0);
    }

    @Test
    public void loanInformationOffAnUserWithFiveRemainingPaymentsAndTwoUnpaids() {
        User user = getUser();

        user.takeOutALoan(moneyLender, accountManagerService);

        user.payLoan(moneyLender, accountManagerService);

        assertEquals(moneyLender.remainingPayments(user, accountManagerService), 5);
        assertEquals(moneyLender.unpaidPayments(user), 0);

        user.takeCash(700.00, accountManagerService);

        user.payLoan(moneyLender, accountManagerService);
        user.payLoan(moneyLender, accountManagerService);

        assertEquals(moneyLender.remainingPayments(user, accountManagerService), 5);
        assertEquals(moneyLender.unpaidPayments(user), 2);
    }

    protected User getUser() {
        User newUser = userFactory.user();

        userDao.save(newUser);

        return newUser;
    }

    @Test
    public void loanInformationOffAnUserWithSomeActivity() {
        User user = getUser();
        user.takeOutALoan(moneyLender, accountManagerService);

        user.payLoan(moneyLender, accountManagerService);
        user.payLoan(moneyLender, accountManagerService);

        assertEquals(moneyLender.remainingPayments(user, accountManagerService), 4);
        assertEquals(moneyLender.unpaidPayments(user), 0);

        user.takeCash(500.00, accountManagerService);

        user.payLoan(moneyLender, accountManagerService);

        assertEquals(moneyLender.remainingPayments(user, accountManagerService), 4);
        assertEquals(moneyLender.unpaidPayments(user), 1);

        user.cashDeposit(1500.00, accountManagerService);

        user.payLoan(moneyLender, accountManagerService);
        user.payLoan(moneyLender, accountManagerService);

        assertEquals(moneyLender.remainingPayments(user, accountManagerService), 1);
        assertEquals(moneyLender.unpaidPayments(user), 0);
    }
}
