package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepositByCreditCardTest extends MoneyTransactionTest {

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
}

