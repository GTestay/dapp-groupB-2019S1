package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.DepositException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.DepositMoneyValidator;
import org.junit.Test;
import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DepositByCashTest {

    private User user = UserFactory.user();
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

        amoutValidations();
    }

    private void amoutValidations() {
        try {
            DepositByCash.create(user, date, -200.00);
            fail();
        } catch (DepositException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.AMOUNT_HAS_NEGATIVE_VALUE);
        }
    }

    private void dateValidations() {
        try {
            DepositByCash.create(user, null, amount);
            fail();
        } catch (DepositException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.DEPOSIT_MONEY_IS_INVALID_WITHOUT_DATE);
        }
    }

    private void userValidations() {
        try {
            DepositByCash.create(null, date, amount);
            fail();
        } catch (DepositException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.DEPOSIT_MONEY_IS_INVALID_WITHOUT_USER);
        }
    }

}

