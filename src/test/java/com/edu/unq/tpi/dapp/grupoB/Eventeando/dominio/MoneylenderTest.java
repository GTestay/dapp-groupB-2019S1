package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoneylenderTest {

    private UserFactory userFactory;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void getActualLoansForAllTheUsers() {
        User arya = userFactory.user();
        User sansa = userFactory.user();

        Moneylender moneylender = Moneylender.get();

        arya.takeOutALoan();
        sansa.takeOutALoan();

        assertHasLoanFrom(moneylender, arya);
        assertHasLoanFrom(moneylender, sansa);
    }

    private void assertHasLoanFrom(Moneylender moneylender, User user) { assertTrue(containLoan(moneylender.actualLoans(), user)); }

    private boolean containLoan(ArrayList<Loan> actualLoans, User owner) { return actualLoans.stream().anyMatch(loan -> loan.isOwner(owner)); }

    @Test
    public void loanInformationOffAnUserWithFiveRemainingPayments() {
        User user = userFactory.user();
        Moneylender moneylender = Moneylender.get();

        user.takeOutALoan();

        user.payLoan();

        assertEquals(moneylender.remainingPayments(user), 5);
        assertEquals(moneylender.unpaidPayments(user), 0);
    }

    @Test
    public void loanInformationOffAnUserWithFiveRemainingPaymentsAndTwoUnpaids() {
        User user = userFactory.user();
        Moneylender moneylender = Moneylender.get();

        user.takeOutALoan();

        user.payLoan();

        assertEquals(moneylender.remainingPayments(user), 5);
        assertEquals(moneylender.unpaidPayments(user), 0);

        user.takeCash(700.00);

        user.payLoan();
        user.payLoan();

        assertEquals(moneylender.remainingPayments(user), 5);
        assertEquals(moneylender.unpaidPayments(user), 2);
    }

    @Test
    public void loanInformationOffAnUserWithSomeActivity() {
        User user = userFactory.user();
        Moneylender moneylender = Moneylender.get();
        user.takeOutALoan();

        user.payLoan();
        user.payLoan();

        assertEquals(moneylender.remainingPayments(user), 4);
        assertEquals(moneylender.unpaidPayments(user), 0);

        user.takeCash(500.00);

        user.payLoan();

        assertEquals(moneylender.remainingPayments(user), 4);
        assertEquals(moneylender.unpaidPayments(user), 1);

        user.cashDeposit(1500.00);

        user.payLoan();
        user.payLoan();

        assertEquals(moneylender.remainingPayments(user), 1);
        assertEquals(moneylender.unpaidPayments(user), 0);
    }
}
