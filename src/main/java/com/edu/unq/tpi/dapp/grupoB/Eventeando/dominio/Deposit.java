package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

public abstract class Deposit extends MoneyTransaction {
    public double transactionalValue() { return amount; }
}
