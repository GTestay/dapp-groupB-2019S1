package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneyTransactionException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.MoneyTransactionValidator;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DepositByCashTest {

    private UserFactory userFactory = new UserFactory();
    private User user = userFactory.user();
    private LocalDate date = LocalDate.now();
    private double amount = 1000.00;

    @Test
    public void creationOfANewDepositMoneyByCash() {
        DepositByCash depositByCash = DepositByCash.create(user, date, amount);

        assertEquals(user, depositByCash.user());
        assertEquals(date, depositByCash.date());
        assertEquals(amount, depositByCash.amount(), 0);
    }

    @Test
    public void depositMoneyByCashValidations() {
        userValidations();

        dateValidations();

        amountValidations();
    }

    private void amountValidations() {
        try {
            DepositByCash.create(user, date, -200.00);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.AMOUNT_HAS_NEGATIVE_VALUE);
        }
    }

    private void dateValidations() {
        try {
            DepositByCash.create(user, null, amount);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE);
        }
    }

    private void userValidations() {
        try {
            DepositByCash.create(null, date, amount);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER);
        }
    }
}

