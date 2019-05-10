package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExtractionTest extends MoneyTransactionTest {

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
}

