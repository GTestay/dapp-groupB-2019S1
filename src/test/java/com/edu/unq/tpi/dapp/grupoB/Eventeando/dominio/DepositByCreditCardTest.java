package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.DepositException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.DepositMoneyValidator;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DepositByCreditCardTest {

    private User user = UserFactory.user();
    private LocalDate date = LocalDate.now();
    private double amount = 1000.00;

    @Test
    public void creationOfANewDepositMoneyByCreditCard() {
        DepositByCreditCard depositByCreditCard = DepositByCreditCard.create(user, date, amount);

        assertEquals(user, depositByCreditCard.user());
        assertEquals(date, depositByCreditCard.date());
        assertEquals(amount, depositByCreditCard.amount(), 0);
    }

    @Test
    public void depositMoneyByCreditCardValidations() {
        userValidations();

        dateValidations();

        amoutValidations();
    }

    private void amoutValidations() {
        try {
            DepositByCreditCard.create(user, date, -200.00);
            fail();
        } catch (DepositException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.AMOUNT_HAS_NEGATIVE_VALUE);
        }
    }

    private void dateValidations() {
        try {
            DepositByCreditCard.create(user, null, amount);
            fail();
        } catch (DepositException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.DEPOSIT_MONEY_IS_INVALID_WITHOUT_DATE);
        }
    }

    private void userValidations() {
        try {
            DepositByCreditCard.create(null, date, amount);
            fail();
        } catch (DepositException error) {
            assertEquals(error.getMessage(), DepositMoneyValidator.DEPOSIT_MONEY_IS_INVALID_WITHOUT_USER);
        }
    }

}

