package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneyTransactionException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validator.MoneyTransactionValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ExtractionTest {

    private UserFactory userFactory;
    private User user;

    private LocalDate date = LocalDate.now();
    private double amount = 1000.00;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
        user = this.userFactory.user();
    }

    @Test
    public void creationOfANewExtraction() {
        Extraction extraction = Extraction.create(user, date, amount);

        assertEquals(user, extraction.user());
        assertEquals(date, extraction.date());
        assertEquals(amount, extraction.amount(), 0);
    }

    @Test
    public void extractionValidations() {
        userValidations();

        dateValidations();

        amountValidations();
    }

    private void amountValidations() {
        try {
            Extraction.create(user, date, -200.00);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.AMOUNT_HAS_NEGATIVE_VALUE);
        }
    }

    private void dateValidations() {
        try {
            Extraction.create(user, null, amount);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE);
        }
    }

    private void userValidations() {
        try {
            Extraction.create(null, date, amount);
            fail();
        } catch (MoneyTransactionException error) {
            assertEquals(error.getMessage(), MoneyTransactionValidator.MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER);
        }
    }
}

