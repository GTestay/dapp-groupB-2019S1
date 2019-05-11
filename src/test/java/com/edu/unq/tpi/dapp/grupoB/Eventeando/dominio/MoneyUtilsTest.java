package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.javamoney.moneta.Money;

public class MoneyUtilsTest {
    static Money pesos(Double amount) {
        return Money.of(amount, "ARS");
    }
}