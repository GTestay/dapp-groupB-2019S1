package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.DepositMoneyException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.DepositMoneyValidator;
import org.junit.Test;
import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DepositMoneyByCashTest {

    private User user = UserFactory.user();
    private LocalDate date = LocalDate.now();
    private double amount = 1000.00;

    @Test
    public void creationOfANewDepositMoneyByCash() {
        DepositMoneyByCash depositMoneyByCash = DepositMoneyByCash.create(user, date, amount);

        assertEquals(user, depositMoneyByCash.user());
        assertEquals(date, depositMoneyByCash.date());
        assertEquals(amount, depositMoneyByCash.amount(), 0);
    }

    @Test
    public void depositMoneyByCashValidations() {
        userValidations();

        dateValidations();

        amoutValidations();
    }

    private void amoutValidations() {
        try {
            DepositMoneyByCash.create(user, date, -200.00);
            fail();
        } catch (DepositMoneyException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.AMOUNT_HAS_NEGATIVE_VALUE);
        }
    }

    private void dateValidations() {
        try {
            DepositMoneyByCash.create(user, null, amount);
            fail();
        } catch (DepositMoneyException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.DEPOSIT_MONEY_IS_INVALID_WITHOUT_DATE);
        }
    }

    private void userValidations() {
        try {
            DepositMoneyByCash.create(null, date, amount);
            fail();
        } catch (DepositMoneyException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.DEPOSIT_MONEY_IS_INVALID_WITHOUT_USER);
        }
    }

}

