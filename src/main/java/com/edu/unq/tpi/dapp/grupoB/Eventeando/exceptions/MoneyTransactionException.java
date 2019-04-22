package com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions;

public class MoneyTransactionException extends RuntimeException {
    public MoneyTransactionException(String depositErrorMessage) { super(depositErrorMessage); }
}
