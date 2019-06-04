package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneyTransactionException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.MoneyTransactionValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DepositByCreditCardTest {

    private UserFactory userFactory;
    private User user;
    private LocalDate date = LocalDate.now();
    private double amount = 1000.00;
    private YearMonth dueDate = YearMonth.now().plusMonths(1);
    private String cardNumber = "4111111111111111";

    @Before
    public void setUp() throws Exception {
        userFactory = new UserFactory();
        user = userFactory.user();
    }

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

    private void amountValidations() {
        try {
            DepositByCreditCard.create(user, date, -200.00, dueDate, cardNumber);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.AMOUNT_HAS_NEGATIVE_VALUE);
        }
    }

    private void dateValidations() {
        try {
            DepositByCreditCard.create(user, null, amount, dueDate, cardNumber);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE);
        }
    }

    private void userValidations() {
        try {
            DepositByCreditCard.create(null, date, amount, dueDate, cardNumber);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER);
        }
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
            DepositByCreditCard.create(user, date, amount, dueDate, "1234");
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.INVALID_CREDIT_CARD_NUMBER);
        }
    }
}

