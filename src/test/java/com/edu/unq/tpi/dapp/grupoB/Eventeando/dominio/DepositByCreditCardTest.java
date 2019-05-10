package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneyTransactionException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.MoneyTransactionValidator;
import org.junit.Test;

import java.time.YearMonth;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DepositByCreditCardTest extends MoneyTransactionTest {

    private YearMonth dueDate = YearMonth.now().plusMonths(1);
    private Long cardNumber = 4111111111111111L;

    @Test
    public void creationOfANewDepositMoneyByCreditCard() {
        DepositByCreditCard depositByCreditCard = DepositByCreditCard.create(user, date, amount, dueDate, cardNumber);

        assertEquals(user, depositByCreditCard.user());
        assertEquals(date, depositByCreditCard.date());
        assertEquals(amount, depositByCreditCard.amount(), 0);
        assertEquals(dueDate, depositByCreditCard.dueDate());
        assertEquals(cardNumber, depositByCreditCard.cardNumber());
    }

    @Test
    public void depositMoneyByCreditCardValidations() {
        userValidations();

        dateValidations();

        amountValidations();

        dueDatevalidations();

        cardNumbervalidations();
    }

    private void dueDatevalidations() {
        try {
            DepositByCreditCard.create(user, date, amount, YearMonth.now().minusMonths(1), cardNumber);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.INVALID_DUE_DATE);
        }
    }

    private void cardNumbervalidations() {
        try {
            DepositByCreditCard.create(user, date, amount, dueDate, 1234L);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.INVALID_CREDIT_CARD_NUMBER);
        }
    }
}

