package com.edu.unq.tpi.dapp.grupoB.Eventeando.exception;

public class MoneyTransactionException extends RuntimeException {
    public MoneyTransactionException(String depositErrorMessage) { super(depositErrorMessage); }
}
