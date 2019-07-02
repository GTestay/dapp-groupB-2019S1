package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
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
public class MoneylenderTest {

    private UserFactory userFactory;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private Moneylender moneyLender;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void getActualLoansForAllTheUsers() {
        User arya = userFactory.user();
        User sansa = userFactory.user();

        arya.takeOutALoan(moneyLender, accountManager);
        sansa.takeOutALoan(moneyLender, accountManager);

        assertHasLoanFrom(moneyLender, arya);
        assertHasLoanFrom(moneyLender, sansa);
    }

    private void assertHasLoanFrom(Moneylender moneylender, User user) { assertTrue(containLoan((ArrayList<Loan>) moneylender.actualLoans(), user)); }

    private boolean containLoan(ArrayList<Loan> actualLoans, User owner) { return actualLoans.stream().anyMatch(loan -> loan.isOwner(owner)); }

    @Test
    public void loanInformationOffAnUserWithFiveRemainingPayments() {
        User user = userFactory.user();

        user.takeOutALoan(moneyLender, accountManager);

        user.payLoan(accountManager, moneyLender);

        assertEquals(moneyLender.remainingPayments(user, accountManager), 5);
        assertEquals(moneyLender.unpaidPayments(user), 0);
    }

    @Test
    public void loanInformationOffAnUserWithFiveRemainingPaymentsAndTwoUnpaids() {
        User user = userFactory.user();

        user.takeOutALoan(moneyLender, accountManager);

        user.payLoan(accountManager, moneyLender);

        assertEquals(moneyLender.remainingPayments(user, accountManager), 5);
        assertEquals(moneyLender.unpaidPayments(user), 0);

        user.takeCash(700.00, accountManager);

        user.payLoan(accountManager, moneyLender);
        user.payLoan(accountManager, moneyLender);

        assertEquals(moneyLender.remainingPayments(user, accountManager), 5);
        assertEquals(moneyLender.unpaidPayments(user), 2);
    }

    @Test
    public void loanInformationOffAnUserWithSomeActivity() {
        User user = userFactory.user();
        user.takeOutALoan(moneyLender, accountManager);

        user.payLoan(accountManager, moneyLender);
        user.payLoan(accountManager, moneyLender);

        assertEquals(moneyLender.remainingPayments(user, accountManager), 4);
        assertEquals(moneyLender.unpaidPayments(user), 0);

        user.takeCash(500.00, accountManager);

        user.payLoan(accountManager, moneyLender);

        assertEquals(moneyLender.remainingPayments(user, accountManager), 4);
        assertEquals(moneyLender.unpaidPayments(user), 1);

        user.cashDeposit(1500.00, accountManager);

        user.payLoan(accountManager, moneyLender);
        user.payLoan(accountManager, moneyLender);

        assertEquals(moneyLender.remainingPayments(user, accountManager), 1);
        assertEquals(moneyLender.unpaidPayments(user), 0);
    }
}
