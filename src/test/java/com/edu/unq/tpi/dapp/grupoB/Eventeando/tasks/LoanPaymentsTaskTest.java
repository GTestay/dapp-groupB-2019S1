package com.edu.unq.tpi.dapp.grupoB.Eventeando.tasks;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoanPaymentsTaskTest {

    private UserFactory userFactory = new UserFactory();
    private LoanPaymentsTask service = new LoanPaymentsTask();

    @Test
    public void forUsersWithALoanInProgressChargeTheLoan(){
        User user = userFactory.user();
        User anotherUser = userFactory.user();
        anotherUser.cashDeposit(500.00);

        user.takeOutALoan();

        service.execute();

        assertEquals(800.00, user.balance(), 0);
        assertEquals(500.00, anotherUser.balance(), 0);
    }

    @Test
    public void forUsersWhoAlreadyPayedTheLoanDoNotDoAnything(){
        User user = userFactory.user();
        user.cashDeposit(600.00);
        User anotherUser = userFactory.user();

        user.takeOutALoan();
        anotherUser.takeOutALoan();

        int i;
        for (i = 0; i < 6; i++) {
            user.payLoan();
        }

        service.execute();

        assertEquals(400, user.balance(), 0);
        assertEquals(800, anotherUser.balance(), 0);
    }
}
