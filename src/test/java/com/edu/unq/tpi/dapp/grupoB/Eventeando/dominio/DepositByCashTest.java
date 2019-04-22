package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepositByCashTest extends MoneyTransactionTest {

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
}

